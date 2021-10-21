public class HappyBirthday {
    public static void main(String[] args) {
        LovelyGirl iphone=new LovelyGirl();
        while (true){
            iphone.happiness++;
            iphone.sadness--;
        }
    }
}
class LovelyGirl{
    final int age=18;
    double happiness;
    double sadness;
}
