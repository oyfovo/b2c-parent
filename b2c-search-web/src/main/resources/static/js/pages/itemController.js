var vue = new Vue({
    mixins:[base],//继承了base
    el:'#itemView',
    data:{
        basePath:'/search/item',//基本路径
        searchParam:{//查询参数
            pageNum:1, //第一页
            pageSize:10, //每页显示4条
            keyword:"",//搜索关键字
        },
    },
})