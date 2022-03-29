var vue = new Vue({
    mixins:[base],//继承了base
    el:'#goodsView',
    data:{
        basePath:'/seller/goods',//基本路径
        itemCatList1:[],//一级分类列表
        itemCatList2:[],//二级分类列表
        itemCatList3:[],//三级分类列表
        brandList:[],//品牌列表
        specUnionList:[],//规格联合对象列表
        checkTrue:1,//复选框选中
        checkFalse:0,//复选框取消
        pic:null,//上传图片实体
        itemImage:{
            color:"",//颜色
            uri:""//图片地址
        },
        entity:{
            goods:{//提交给goods表
                brandId:0,//品牌编号
                category1Id:0,//一级分类编号
                category2Id:0,//二级分类编号
                category3Id:0,//三级分类编号
                typeTemplateId:0,//类型模板编号
            },
            goodsDesc:{//提交给goods_desc表
                customAttributeItems:[],//扩展属性列表
                specificationItems:[],//规格选项列表
                itemImages:[],//颜色和图片对象列表
            },
            itemList:[],//SKU列表，例如网络、屏幕...
        }
    },
    created:function () {//初始化方法
        //调用查询一级菜单
        this.findItemCatsByParentId(0,'itemCatList1')
    },
    methods:{
        findItemCatsByParentId:function (parentId,listFlag) {
            var uri = '/seller/itemCats/'+parentId;
            //异步请求
            axios.get(uri).then(res =>{
                this.$data[listFlag] = res.data;
            })
        },
        findTypeTemplateByItemCatId:function (itemCatId) {
            var uri = '/seller/itemCat/'+itemCatId;
            //异步请求
            axios.get(uri).then(res =>{
                this.entity.goods.typeTemplateId = res.data.typeId;
            })
        },
        findBrandIdsAndCustomAttributeItemsByTypeTemplateId:function (typeTemplateId) {
            var uri = '/seller/typeTemplate/'+typeTemplateId;
            //异步请求
            axios.get(uri).then(res =>{
                //填充品牌
                this.brandList = JSON.parse(res.data.brandIds);
                //填充扩展属性
                this.entity.goodsDesc.customAttributeItems = JSON.parse(res.data.customAttributeItems);
            })
        },
        save:function () {
            console.debug(this.entity);
        },
        findSpecUnionsByTypeTemplateId:function (typeTemplateId) {
            var uri = '/seller/typeTemplate/specUnions/'+typeTemplateId;
            //异步请求
            axios.get(uri).then(res => {
                //动态创建spec的保存结构
                this.createSpecificationItemsStructure(res)
                //把结果放到data中
                this.specUnionList = res.data;
                //测试打印
                //console.debug(this.specUnionList);
            });
        },
        createSpecificationItemsStructure:function (res) {
            //遍历res
            res.data.forEach((item,index,arr) => {
                // 设置响应式属性
                // 如果没有设置，则用户选择的规格信息无法更新到specificationItems中
                this.$set(this.entity.goodsDesc.specificationItems,index,{
                    attributeName:item.spec.specName,//规格名称
                    attributeValue:[],//规格明细列表，暂时设为空列表
                })
            })
        },
        // 创建itemList保存结构
        createItemListStructure : function() {
            var structure = [ {// 初始化保存结构
                spec : {},
                price : 0,
                num : 999,
                status : "0",
                isDefault : "0"
            } ];
            this.entity.goodsDesc.specificationItems.forEach((item,index, arr) => {
                // 添加spec中的列，也就是给spec增加一个键值对
                structure = this.addColumn(structure, item.attributeName, item.attributeValue);
            });
            // console.debug(structure);
            this.$set(this.entity, "itemList", structure);
        },
        // 添加spec中的列
        addColumn : function(list, columnName, columnValues) {
            var newList = [];
            list.forEach((item, index, arr) =>{
                columnValues.forEach((columnValue, ind, ar) =>{
                    // 必须以JSON的方式进行克隆
                    // 直接修改item的话，会覆盖之前push进去的数据
                    var row = JSON.parse(JSON.stringify(item));
                    row.spec[columnName] = columnValue;
                    newList.push(row);
                });
            });
            // 如果不判断length，会导致createItemListStructure中的循环结束。
            if (newList.length == 0)
                return list;
            return newList;
        },
        selectFile:function (event) {//选择了哪个图片
            console.debug(event.target.files[0]);
            this.pic = event.target.files[0];
        },
        uploadImage:function () {//图片上传
            //创建一个表单对象
            var formData = new FormData();
            //给表单添加属性
            formData.append("pic",this.pic);
            //异步请求
            axios.post("/seller/upload",formData,{
                headers : {
                    "Content-Type" : "multipart/form-data"
                }
            }).then(res => {
                //保存到Data中
                this.itemImage.uri = res.data.msg;
                console.debug(res.data.msg)
            })
        },
        initImage:function () {//清空图片
            this.itemImage = {
                color:"",
                uri:""
            };
        },
        addImage:function () {//保存图片
            this.entity.goodsDesc.itemImages.push(this.itemImage);//把对象放到列表中
        },
        deleteImage:function (index) {//删除图片
            this.entity.goodsDesc.itemImages.splice(index,1);
        }
    },
    watch:{
        //key-需要观察的属性名
        //value-属性发生变化时执行的回调函数
        //val：当前值
        //oldVal：改变之前的值
        "entity.goods.category1Id":function (val,oldVal) {
            this.findItemCatsByParentId(val,'itemCatList2');
        },
        "entity.goods.category2Id":function (val,oldVal) {
            this.findItemCatsByParentId(val,'itemCatList3');
        },
        "entity.goods.category3Id":function (val,oldVal) {
            this.findTypeTemplateByItemCatId(val);
        },
        "entity.goods.typeTemplateId":function (val,oldVal) {
            //查询品牌和扩展属性
            this.findBrandIdsAndCustomAttributeItemsByTypeTemplateId(val);
            //查询规格和规格明细
            this.findSpecUnionsByTypeTemplateId(val);
        },
    }
})