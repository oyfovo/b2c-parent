var vue = new Vue({
    mixins:[base],//继承了base
    el:'#contentView',
    data:{
        basePath:'/portal/content',//基本路径
        contentList:[],//广告列表
    },
    created:function () {//初始方法
        this.findByCategoryId(1);//查询首页轮播广告
    },
    methods:{
        findByCategoryId:function (categoryId) {//根据广告类型id查询广告对象
            //异步请求
            axios.get(this.basePath+"s/"+categoryId).then(res => {
                this.contentList = res.data;
            });
        }
    }
})