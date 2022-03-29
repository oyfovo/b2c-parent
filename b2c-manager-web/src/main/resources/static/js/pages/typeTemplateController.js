//加入VUE的下拉列表插件
Vue.component('v-select',VueSelect.VueSelect);

var vue = new Vue({
    mixins:[base],//继承了base
    el:'#typeTemplateView',
    data:{
        basePath:'/manager/typeTemplate',//基本路径
        brandList:[],
        specificationList:[]
    },
    created:function () {
        //给entity设置扩展属性，其它属性通过v-model设置了
        this.$set(this.entity,'customAttributeItems',[]);
        //调用查询方法
        this.findBrands();
        this.findSpecifications();
    },
    methods:{
        jsonToString:function (jstr,key) {
            //把JSON字符串转换成JSON对象
            var jsonArr = JSON.parse(jstr);
            var newArr = [];
            //遍历集合
            jsonArr.forEach(function (item,index) {
                newArr.push(item[key]);
            });
            return newArr.join();//使用逗号分隔数组，并返回结果
        },
        findBrands:function () {//查询所有品牌
            axios.get('/manager/brand').then(res => { //使用ECMA6.0的标准，绕开了匿名函数，可以直接获取this关键了
                this.brandList = res.data;
            })
        },
        findSpecifications:function () {//查询所有规格
            axios.get('/manager/specification').then(res => {
                this.specificationList = res.data;
            })
        },
        findById:function (id) {//重写父类的方法
            var self = this;
            //异步查询
            axios.get(this.basePath+"/"+id).then(function (res) {
                self.entity = res.data;
                //处理数据：把JSON字符串换行成JSON对象
                self.entity.brandIds = JSON.parse(self.entity.brandIds);
                self.entity.specIds = JSON.parse(self.entity.specIds);
                self.entity.customAttributeItems = JSON.parse(self.entity.customAttributeItems)
            });
        },
        initSelectAndRows:function () {//新建的时候，清空数据
            this.entity.id = null;
            this.entity.name = '';
            this.entity.specIds = [];
            this.entity.brandIds = [];
            this.entity.customAttributeItems = [];
        },
        addRow:function () {//新增扩展属性
            this.entity.customAttributeItems.push({});
        },
        deleteRow:function (index) {//删除扩展属性
            this.entity.customAttributeItems.splice(index,1);
        }
    }
})