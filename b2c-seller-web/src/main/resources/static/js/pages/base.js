//初始化分页插件
Vue.component('paginate',VuejsPaginate)

var base = {
    data:function () {
        return {
            basePath:'',//基本路径
            entity:{},//通过id查询的对象
            ids:[],//删除的id数组
            pageCount:0,//总页数
            searchParam:{//查询参数
                pageNum:1, //第一页
                pageSize:4 //每页显示4条
            },
            searchResult:{
                total:0,
                list:[]
            },//查询结果集
        }
    },
    created:function(){//构造方法

    },
    methods:{
        findPage:function () {//分页查询
            var self = this;
            //异步请求
            axios.get(this.basePath+'s',{
                params:this.searchParam //地址栏传参：仅限GET请求才有params
            }).then(function (res) {
                console.debug(res.data);
                //把结果存到自定义的属性中
                self.searchResult = res.data;
                //计算总页数
                self.pageCount = Math.ceil(res.data.total/self.searchParam.pageSize);
            })
        },
        findById:function (id) {//通过id查询品牌
            var self = this;
            //异步查询
            axios.get(this.basePath+"/"+id).then(function (res) {
                self.entity = res.data;
            })
        },
        saveOrUpdate:function () {//保存或修改
            var self = this;
            if(this.entity.id){//修改
                //异步请求
                axios.put(this.basePath,this.entity).then(function (res) {
                    //打印日志
                    console.debug(res);
                    //刷新数据
                    self.findPage();
                })
            }else{//保存
                //异步请求
                //异步请求
                axios.post(this.basePath,this.entity).then(function (res) {
                    //打印日志
                    console.debug(res);
                    //刷新数据
                    self.findPage();
                })
            }
        },
        deleteByIds:function () {//批量删除
            var self = this;
            if(this.ids.length>0){
                if(confirm('确定要删除吗？')){
                    //打印ids
                    console.debug(this.ids);
                    //异步调用删除方法
                    axios.delete(this.basePath+"/"+this.ids).then(function (res) {
                        //打印日志
                        console.debug(res);
                        //刷新页面
                        self.findPage();
                    })
                }
            }

        }
    }
}