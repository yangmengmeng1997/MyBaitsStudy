package com.Exercise;

import java.util.*;
public class Exercise1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();//用来存储结果
        List<Map.Entry<String, Integer>> list = new ArrayList();
//        int t=9;
        while (in.hasNextLine()) {
            String newString = in.nextLine();
            if(newString.equals("")) break;
            String result = newString.replaceAll("-", "").replaceAll("\'s", "").replaceAll("\'", "").replaceAll( "[\\pP+~$`^=|<>～｀＄＾＋＝｜＜＞￥×]" , "").replaceAll("\\d+", "");
            map = countWords(result, map);
            list = sortByValue(map);
        }
        int flag = 0;
        int flag2 = 0;
        while (flag2 < list.size()) {
            int judge = list.get(flag).getValue();
            List<String> times = new ArrayList<>();
            for (int i = flag; i < list.size(); i++) {
                flag2++;
                if (list.get(i).getValue() != judge) {
                    int tem = 0;
                    flag = i;
                    tem = flag;
                    i = tem;
                    break;
                } else
                    times.add(list.get(i).getKey().toLowerCase());
            }
            Collections.sort(times);
            for (int i = 0; i < times.size(); i++) {
                System.out.println(times.get(i).toLowerCase());
            }
        }
    }
    public static LinkedHashMap<String, Integer> countWords(String string, LinkedHashMap<String, Integer> map) {
        String[] str = string.split(" ");//获得单词
        for (int i = 0; i < str.length; i++) {
            if (!("".equals(str[i]))) {
                Iterator<String> it = map.keySet().iterator();
                boolean exist = false;
                while (it.hasNext()) {
                    String key = it.next();
                    if (key.equalsIgnoreCase(str[i])) {
                        exist = true;
                        map.put(key, map.get(key) + 1);
                    }
                }
                if (exist == false) {
                    map.put(str[i], 1);
                }
            }
        }
        return map;
    }
    public static List<Map.Entry<String, Integer>> sortByValue(LinkedHashMap<String, Integer> map) {//按照value也就是出现的次数排序
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            //降序排序
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        return list;
    }

}
