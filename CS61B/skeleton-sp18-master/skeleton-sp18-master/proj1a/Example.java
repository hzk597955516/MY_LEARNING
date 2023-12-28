import java.util.*;

public class Example {
    public static void main(String[] args){
        Example.Example1();
        Example.Example2();
    }

    public static void Example1(){
        List<Integer> L = new ArrayList<>();
        L.add(5);
        L.add(10);
        L.add(15);
        Iterator<Integer> seer = L.iterator();
        System.out.println(L);
        while (seer.hasNext()){
            System.out.println(seer.next());
        }
    }

    public static void Example2(){
        Set<String> s = new HashSet<>();
        s.add("Tokyo");
        s.add("Lagos");
        System.out.println(s.contains("Tokyo"));
        for (String city : s){
            System.out.println(city);
        }
    }
}
