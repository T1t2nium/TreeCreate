package TreeStudy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基于树形结构数据储存的容器
 * @param <E>
 */
public class MyTree<E> {

    private Map<E ,E> map = new HashMap<>();//子节点向根节点的映射
    private Map<E,List<E>> map2 = new HashMap<>();//根节点向子节点的映射
    /**
     * 添加元素
     * @param parent
     * @param item
     */
    public void add(E parent,E item){
        //子->根节点映射
        map.put(item,parent);
        //根->子节点映射
        List<E>  list= this.map2.get(parent);
        //判断当前节点是否含有子节点，没有则创建List节点
        if(list ==null){
            list = new ArrayList<>();
            this.map2.put(parent,list);
        }
        list.add(item);
    }

    /**
     * 获取父节点
     * @return
     */
    public E getParent(E item){return this.map.get(item);}

    /**
     * 获取子节点
     * @return
     */
    public List getSon(E item){return this.map2.get(item);}

    /**
     * 获取兄弟节点
     * @return
     */
    public List getBro(E item){
        //获取父节点
        E parent = this.getParent(item);
        List<E> list = getGraChildren(parent);
        List<E> brother = new ArrayList<>();

        if(list !=null){
            brother.addAll(list);
            brother.remove(item);
        }
        return brother;}

    /**
     * 获取祖先节点
     * @return
     */
    public List getForeFa(E item){
        //获取父节点
        E parent = this.getParent(item);
        //递归获取parent节点的父节点
        if(parent==null){
            return new ArrayList<>();
        }
        List<E> list = this.getForeFa(parent);
        //将递归到的所有元素返回到List中
        list.add(parent);
        return list;}

    /**
     * 获取子孙节点
     * @return
     */
    public List getGraChildren(E item){
        //存放所有子孙节点
        List<E> list = new ArrayList<>();
        //获取当前节点的子节点
        List<E> child = getSon(item);
        //结束递归
        if(child == null){
            return list;
        }
        //遍历子节点
        for(int i = 0;i<child.size();i++){
            //获取元素
            E ele = child.get(i);

            List<E> temp = this.getGraChildren(ele);
            list.add(ele);
            list.addAll( temp);
        }
        return list;}

    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        MyTree<String> myTree = new MyTree<>();

        myTree.add("root","生物");
        myTree.add("生物","植物");
        myTree.add("生物","动物");
        myTree.add("生物","菌类");
        myTree.add("动物","脊椎动物");
        myTree.add("动物","脊索动物");
        myTree.add("动物","腔肠动物");
        myTree.add("脊椎动物","哺乳动物");
        myTree.add("脊椎动物","鱼类");
        myTree.add("哺乳动物","猫");
        myTree.add("哺乳动物","人");
        myTree.add("哺乳动物","牛");
        System.out.println("测试获取父节点");
        String parent = myTree.getParent("鱼类");
        System.out.println(parent);
        System.out.println("测试获取子节点");
        List<String> child = myTree.getSon("哺乳动物");
        for (int i = 0;i< child.size();i++){
            System.out.println(child.get(i));
        }
        System.out.println("测试获取祖先节点");
        List<String> forFather = myTree.getForeFa("人");
        for (int i = 0;i< forFather.size();i++){
            System.out.println(forFather.get(i));
        }


    }
}
