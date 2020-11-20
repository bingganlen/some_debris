import java.util.ArrayList;
import java.util.List;

public class listTest {
    public static void main(String[] args) {
        List labelTraceAll = new ArrayList();
        List labelTraces = new ArrayList();

        labelTraceAll.add(11);
        labelTraceAll.add(22);
        labelTraceAll.add(33);
        labelTraces.add(11);
        labelTraces.add(33);

        int size = labelTraces.size();
        for(int j=size-1;j>=0;j--){
            boolean flag =true;
            for(int i=0;i<labelTraceAll.size();i++){
                //如果两个集合都是对象的list
                if(labelTraces.get(j) == labelTraceAll.get(i)){//如果是int类型比较用== 如果是string类型换成equal
                    flag =false;
                    System.out.println("需改变 i: "+i +"  , j值为"+ j         +  "    "+labelTraces.get(j));
                }
            }
//                        if(flag){
//                            list2.remove(j);
//                        }
        }
    }
}
