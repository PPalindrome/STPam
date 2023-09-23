export default{
    title:{
        name:'STPam：Spatiotemporal Process Analysis and Mining'
    },
    header:{
        welcome:'Welcome：',
        visitor:'Visitor',
        login:'Login',
        register:'Register',
        exit:'Exit',
        help:'Help',
        language:'Language'
    },
    menu:{
        dataAnalysis:{
            name:'Datasets',
            view:{
                name:'View Data',
                title:'Upload Datasets',
                chooseLocalData:'Choose Local Data',
                uploadToServer:'Upload Server',
                warning:'File size must not exceed 20GB',
                adapt:'Adapt external data sources',
                table:{
                    name:'Dataset name',
                    total:'Total',
                    page:'page',
                    goto:'Go to'
                }
            },
            pretreatment:{
                name:'Preprocessing',
                title:'Select method of data preprocessing',
                op1:'Normalization',
                op2:'Time interpolation',
                op3:'Spatial interpolation',
                op4:'Smoothing',
                op5:'Splicing',
            },
            adapt:{
                name:'Adapt Sources',
                datasourceSetting:'Data source configuration',
                datasource:'Data source',
                warning1:'Choose a data source for STPam',
                databaseSetting:'Database configuration',
                connectionName:'Connection name',
                host:'Host',
                port:'Port',
                warning2:'Please ensure that the address and port number are available',
                databaseName1:'Database name',
                databaseName2:'Server name',
                databaseName3:'Path',
                userName:'username',
                password:'password',
                warning3:'Please select the non-root user available for external connection',
                Advance:'Advance',
            }
        },
        modeling:{
            name:'Modeling',
            theme:'Theme',
            dark:'Dark',
            daytime:'Daytime',
            save:'save',
            model:'Choose a model',
            optimize:"Model optimization",
            sensitive:"Sensitivity analysis",
            confirm:"Are you sure to save the currently changed code?",
            edit:"Edit your code here"
        },
        mission:{
            name:'Model Service',
            title:"Select a spatiotemporal process deep learning model",
            uploadNew:"Upload new model",
            warning:"(Multiple files are uploaded in Zip)",
            command:"Start command",
            outPath:"Output path",
            selectFile:"Select file",
            submit:"Publish model service",
            chooseModel:"Select model service",
            chooseData:"Select a spatiotemporal data set",
            mission:"Submit spatiotemporal process calculation tasks"
        },
        compute:{
            name:'Calculating',
            distribute:'Distributed Computing',
            incremental:'Incremental learning',
            title:"Spatiotemporal process distributed analysis and mining",
            calculate:"Calculate",
            state:"Distributed computing state",
            information:"GPU information",
            start:"Start time",
            end:"End time",
            duration:"Training duration",
            usage:"Graphics memory usage",
            bandwidth:"Graphics memory bandwidth",
        },
        product:{
            name:'Product',
            application:'Demonstration ',
            show:{
                name:'Product Showcase',
                title:'Spatiotemporal Product',
                productName:'Product name',
                modelArch:'Spatiotemporal process model structure',
            }
        },
        setting:{
            name:'Setting',
            model:'Model',
            compute:'Distributed',
            user:'User'
        }
    },
    button:{
        comfirm:'Confirm',
        delete:'Delete',
        choose:'Choose',
        view:'View',
        null:'No Data',
        operation:'Operation',
        date:'Date',
        all:'All',
        fresh:'Refresh',
        download:'download',
        search:'Search',
        description:'Description',
        modelName:'ModelName',
    },
    table:{
        modelName:'Model name',
        modelVersion:"Model version",
        modelFile:"File name",
        dataName:"Dataset name",
        dataPath:"Dataset path",

    }
}