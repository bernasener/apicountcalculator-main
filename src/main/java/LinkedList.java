import java.util.ArrayList;

public class LinkedList {
    Node head;

    static class Node {
        String api_name;
        String method;
        String summary;
        Boolean deprecated;
        Node next;

        Node(String api_name_cons, String method_cons, String summary_cons, Boolean deprecated_cons){
            api_name = api_name_cons;
            method = method_cons;
            summary = summary_cons;
            deprecated = deprecated_cons;
            next = null;
        }
    }

    public static LinkedList insert(LinkedList list, String api_name, String method, String summary, Boolean deprecated){
        Node apiMethod = new Node(api_name, method, summary, deprecated);

        if(list.head == null){
            list.head = apiMethod;
        }
        else{
            Node last = list.head;
            while(last.next != null){
                last = last.next;
            }
            last.next = apiMethod;
        }
        return list;
    }

    public static void display(LinkedList list){
        Node node = list.head;
        int counter = 0;
        while (node != null) {
            counter++;
            System.out.print(counter + "." + "\n");
            System.out.print("API: " + node.api_name + "\n" + "HTTP Method: " +  node.method + "\n" + "Summary: " + node.summary + "\n" + "Deprecation: " + node.deprecated + "\n");
            node = node.next;
            System.out.println("--------------------------------------------------");
        }
        System.out.println();
    }

    public static int httpMethodCount(LinkedList list){
        Node node = list.head;
        int counter = 0;
        while(node != null){
            if(!node.deprecated){
                counter++;
            }
            node = node.next;
        }
        return counter;
    }

    public static LinkedList copyConstructor(LinkedList list, LinkedList dummy){
        Node node = list.head;
        while(node != null){ // Deep copy constructor
            insert(dummy, node.api_name, node.method, node.summary, node.deprecated);
            node = node.next;
        }
        return dummy;
    }

    public static void specificHttpMethodCount(LinkedList list, LinkedList dummy){
        Node node = list.head;
        Node node_ = dummy.head;

        int counter = 0;
        int total = 0;
        String dum_string = "";

        while(node != null) {
            dum_string = node.method;
            while (node_ != null) {
                if (node_.method.equals(node.method) && !node_.deprecated ) {
                    counter++;
                }
                node_ = node_.next;
            }
            System.out.print(dum_string + ": " + counter + "\n");
            node = node.next;
            node_ = dummy.head;
            total += counter;
            counter = 0;
            if (total == httpMethodCount(list)){
                break;
            }
        }
    }

    public static ArrayList<Integer> tagHttpMethodList(LinkedList list) { //In the order of the tags reside in tagCountList
        Node node = list.head;

        ArrayList<String> tagList = tagCountList(list);
        ArrayList<String> methodCount = new ArrayList<String>();
        ArrayList<Integer> httpMethods = new ArrayList<Integer>();

        int key_word_index_0;
        boolean exist = false;
        String api_name_0 = "";

        for (int i = 0; i < tagList.size(); i++) {
            while (node != null) {
                key_word_index_0 = node.api_name.indexOf("/", 1);
                if (key_word_index_0 == -1) {
                    api_name_0 = node.api_name.substring(1);
                } else {
                    api_name_0 = node.api_name.substring(1, key_word_index_0);
                }
                if (tagList.get(i).equals(api_name_0)) {
                    for (int j = 0; j < methodCount.size(); j++) {
                        if(methodCount.get(j).equals(node.method)){
                            exist = true;
                            break;
                        }
                        else {
                            exist = false;
                        }
                    }
                    if(!exist){
                        methodCount.add(node.method);
                    }
                }
                node = node.next;
            }
            httpMethods.add(methodCount.size());
            methodCount.clear();
            node = list.head;
            exist = false;
        }
        return httpMethods;
    }

    public static void classBasedHttpMethodCount(LinkedList list){
        Node node = list.head;

        ArrayList<String> tagList = tagCountList(list);
        ArrayList<Integer> tagBasedMethodCount = tagHttpMethodList(list);
        ArrayList<String> methodCount = new ArrayList<String>();
        ArrayList<String> alreadyDone = new ArrayList<String>();

        int key_word_index_0;
        String api_name_0 = "";
        String searched_method = "";
        boolean exist = false;

        for (int i = 0; i < tagList.size(); i++) {
            System.out.print("*" + tagList.get(i) + "\n");
           for(int j = 0; j < tagBasedMethodCount.get(i); j++) {
                while(node != null){
                    key_word_index_0 = node.api_name.indexOf("/", 1);
                    if (key_word_index_0 == -1) {
                        api_name_0 = node.api_name.substring(1);
                    } else {
                        api_name_0 = node.api_name.substring(1, key_word_index_0);
                    }
                    for(int m = 0; m < alreadyDone.size(); m++){
                        if(alreadyDone.get(m).equals(node.method)){
                            exist = true;
                        }
                    }
                    if(tagList.get(i).equals(api_name_0) && !exist){
                        for (int n = 0; n < methodCount.size(); n++) {
                            if(methodCount.get(n).equals(node.method) && !node.deprecated){
                                methodCount.add(node.method);
                                break;
                            }
                        }
                        if(methodCount.isEmpty()){
                            methodCount.add(node.method);
                        }
                    }
                    node = node.next;
                    exist = false;
                }
                System.out.print("  " + methodCount.get(0) + ": " + methodCount.size() + "\n");
                alreadyDone.add(methodCount.get(0));
                methodCount.clear();
                node = list.head;
            }
            alreadyDone.clear();
            exist = false;
        }
    }

    public static int tagCount(LinkedList list){
        Node node = list.head;
        ArrayList<String> tagList = new ArrayList<String>();

        int key_word_index_0;
        boolean exist = false;
        String api_name_0 = "";

        while(node != null){
            key_word_index_0 = node.api_name.indexOf("/", 1);
            if(key_word_index_0 == -1){
                api_name_0 = node.api_name.substring(1);
            }
            else {
                api_name_0 = node.api_name.substring(1, key_word_index_0);
            }
            for (int i = 0; i < tagList.size(); i++){
                if(tagList.get(i).equals(api_name_0)){
                    exist = true;
                    break;
                }
                else{
                    exist = false;
                }
            }
            if(!exist){
                tagList.add(api_name_0);
            }
            if(tagList.isEmpty()){
                tagList.add(api_name_0);
            }
            node = node.next;
        }
        return tagList.size();
    }

    public static ArrayList<String> tagCountList(LinkedList list){
        Node node = list.head;
        ArrayList<String> tagList = new ArrayList<String>();

        int key_word_index_0;
        Boolean exist = false;
        String api_name_0 = "";

        while(node != null){
            key_word_index_0 = node.api_name.indexOf("/", 1);
            if(key_word_index_0 == -1){
                api_name_0 = node.api_name.substring(1);
            }
            else {
                api_name_0 = node.api_name.substring(1, key_word_index_0);
            }
            for (int i = 0; i < tagList.size(); i++){
                if(tagList.get(i).equals(api_name_0)){
                    exist = true;
                    break;
                }
                else{
                    exist = false;
                }
            }
            if(!exist){
                tagList.add(api_name_0);
            }
            if(tagList.isEmpty()){
                tagList.add(api_name_0);
            }
            node = node.next;
        }
        return tagList;
    }

    public static void detailedOutput(LinkedList list, LinkedList dummy){
        Node node = list.head;
        Node node_ = dummy.head;

        int key_word_index_0;
        String api_name_0 = "";

        int key_word_index_1 = 0;
        String api_name_1 = "";

        int key_word_index_2 = 0;
        String api_name_2 = "";

        int counter_node = 0;
        int counter_node_ = 0;
        int total = 0;

        ArrayList<String> dummy_list = tagCountList(list);

        while(node != null) {
            key_word_index_0 = node.api_name.indexOf("/", 1);
            if (key_word_index_0 == -1) {
                api_name_0 = node.api_name.substring(1);
            } else {
                api_name_0 = node.api_name.substring(1, key_word_index_0);
            }
            for (int i = 0; i < dummy_list.size(); i++) {
                if (dummy_list.get(i).equals(api_name_0)) {
                    System.out.print("//////////////////////////////////////////////////" + "\n");
                    System.out.print("*" + api_name_0 + "\n");
                    while (node_ != null) {
                        key_word_index_1 = node_.api_name.indexOf("/", 1);
                        if (key_word_index_1 == -1) {
                            api_name_1 = node_.api_name.substring(1);
                        } else {
                            api_name_1 = node_.api_name.substring(1, key_word_index_1);
                        }

                        key_word_index_2 = node.api_name.indexOf("/", 1);
                        if (key_word_index_2 == -1) {
                            api_name_2 = node.api_name.substring(1);
                        } else {
                            api_name_2 = node.api_name.substring(1, key_word_index_2);
                        }

                        if (api_name_1.equals(api_name_2)) { //if node.api_name == node_.api_name
                            System.out.print("  -" + node_.method + "\n");
                            System.out.print("      -" + node_.api_name + "\n");
                            System.out.print("      -" + node_.summary + "\n");
                        }
                        counter_node_++;
                        node_ = node_.next;
                        dummy_list.remove(api_name_0);
                    }
                }
            }
            counter_node++;
            node = node.next;
            node_ = dummy.head;
            counter_node_ = 0;
            if (dummy_list.size() == 0){
                break;
            }
        }
    }
}
