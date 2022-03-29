var vue = new Vue({
    mixins:[base],//继承了base
    el:'#specificationView',
    data:{
        basePath:'/manager/specification',//基本路径
    },
    created:function () {
        //动态修改entity的数据结构
        this.$set(this.entity,'spec',{});
        this.$set(this.entity,'specOptionList',[]);
    },
    methods:{
        saveOrUpdate:function () {//重写父类的方法
            var self = this;
            if(this.entity.spec.id){//修改
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
        clearRows:function () {//新建的时候，清空数据
            this.entity.spec = {};
            this.entity.specOptionList = [];
        },
        addRow:function () {//新增时，添加一行
            this.entity.specOptionList.push({});
        },
        deleteRow:function (index) {
            this.entity.specOptionList.splice(index,1);
        }
    }
})