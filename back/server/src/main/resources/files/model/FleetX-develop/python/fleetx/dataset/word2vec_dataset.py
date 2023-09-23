# -*- coding: utf-8 -*
# Copyright (c) 2019 PaddlePaddle Authors. All Rights Reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
import io
import sys
import numpy as np
import paddle.fluid as fluid
import paddle.fluid.incubate.data_generator as dg


class NumpyRandomInt(object):
    def __init__(self, a, b, buf_size=1000):
        self.idx = 0
        self.buffer = np.random.random_integers(a, b, buf_size)
        self.a = a
        self.b = b

    def __call__(self):
        if self.idx == len(self.buffer):
            self.buffer = np.random.random_integers(self.a, self.b,
                                                    len(self.buffer))
            self.idx = 0

        result = self.buffer[self.idx]
        self.idx += 1
        return result


class W2vDataset(dg.MultiSlotDataGenerator):
    def load_resource(self, dict_path, window_size, batch_size, nce_num):
        self.batch_size = batch_size
        self.nce_num = nce_num

        self.id_counts = []
        word_all_count = 0
        with io.open(dict_path, 'r', encoding='utf-8') as f:
            for line in f:
                word, count = line.split()[0], int(line.split()[1])
                self.id_counts.append(count)
                word_all_count += count
        self.id_frequencys = [
            float(count) / word_all_count for count in self.id_counts
        ]
        np_power = np.power(np.array(self.id_frequencys), 0.75)
        self.id_frequencys_pow = np_power / np_power.sum()
        self.cs = np.array(self.id_frequencys_pow).cumsum()
        self.dict_size = len(self.id_counts)
        self.random_generator = NumpyRandomInt(1, window_size + 1)

    def get_context_words(self, words, idx):
        """
        Get the context word list of target word.
        words: the words of the current line
        idx: input word index
        window_size: window size
        """
        target_window = self.random_generator()
        #target_window = 2
        start_point = idx - target_window  # if (idx - target_window) > 0 else 0
        if start_point < 0:
            start_point = 0
        end_point = idx + target_window
        targets = words[start_point:idx] + words[idx + 1:end_point + 1]
        return targets

    def generate_sample(self, line):
        def data_iter():
            neg_array = self.cs.searchsorted(np.random.sample(self.nce_num))
            id_ = 0
            word_ids = [w for w in line.split()]
            for idx, target_id in enumerate(word_ids):
                context_word_ids = self.get_context_words(
                    word_ids, idx)
                for context_id in context_word_ids:
                    neg_id = [ int(str(i)) for i in neg_array ]
                    output = [('input_word', [int(target_id)]), ('true_label', [int(context_id)]), ('neg_label', neg_id)]
                    yield output
                    id_ += 1
                    if id_ % self.batch_size == 0:
                        neg_array = self.cs.searchsorted(np.random.sample(self.nce_num))
        return data_iter


def load_w2v_dataset(inputs, file_list, pipe_command, batch_size, thread_num):
    dataset = fluid.DatasetFactory().create_dataset()
    dataset.set_use_var(inputs)
    dataset.set_pipe_command(pipe_command)
    dataset.set_batch_size(batch_size)
    dataset.set_thread(thread_num)
    dataset.set_filelist(file_list)
    return dataset


if __name__ == "__main__":
    d = W2vDataset()
    dict_path = sys.argv[1]
    window_size = int(sys.argv[2])
    batch_size = int(sys.argv[3])
    nce_num = int(sys.argv[4])
    d.load_resource(dict_path, window_size, batch_size, nce_num)
    d.run_from_stdin()
