import java.util.*;
import java.io.*;

public class Scrabble {
    public static final int scoreArray[] = {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};

    public int conditionedScrabble(String word,int[] characterFrequency,HashMap<Integer,Character> conditions){
        Iterator it = conditions.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            if(word.length()<=(int) pair.getKey()||word.charAt((int) pair.getKey())!=(char)pair.getValue()){
                return 0;
            }else{
                characterFrequency[(char)pair.getValue()-'A']++;
            }
        }
        return getWordScore(word,characterFrequency);
    }

    public int blankTileScrabble(String word, int[] characterFrequency){
        int score=-1;
        int inputFrequency[] = new int[26];
        for(int x=0;x<26;x++){
            characterFrequency[x]=characterFrequency[x]+1;
            for(int i=0;i<26;i++){
                inputFrequency[i] = characterFrequency[i];
            }
            score=Math.max(getWordScore(word,inputFrequency),score);
            characterFrequency[x]=characterFrequency[x]-1;
        }
        return score;
    }


    public int getWordScore(String word, int[] characterFrequency){
        int score=0;
        for(int i=0;i<word.length();i++){
            char currentChar = word.charAt(i);
            if(characterFrequency[currentChar-'A']==0){
                return -1;
            }
            else{
                characterFrequency[currentChar-'A']--;
            }
            score+=scoreArray[currentChar-'A'];
        }
        return score;
    }

    public int getScore(int[] inputFrequency, String fileName,int caseType ,HashMap<Integer,Character> conditions ) throws Exception{
        int score=-1;
        File dictionaryFile=new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(dictionaryFile));
        String word;
        int characterFrequency[] = new int[26];
        while ((word = br.readLine()) != null){

            for(int i=0;i<26;i++){
                characterFrequency[i] = inputFrequency[i];
            }

            if(caseType==0){
                score=Math.max(getWordScore(word, characterFrequency),score);
            }else if(caseType==2){
                score=Math.max(conditionedScrabble(word, characterFrequency,conditions),score);
            }else{
                score=Math.max(blankTileScrabble(word, characterFrequency),score);

            }
        }
        return score;
    }

    public static void main(String args[]) throws Exception{
        int inputFreq[] = new int[26];
        HashMap<Integer,Character> constraints = new HashMap<>();
        Scanner in = new Scanner(System.in);
        System.out.println("Case: ");
        int caseNo = in.nextInt();
        switch(caseNo){
            case 0:
                System.out.println("Enter the 7 letters:");
                for(int i=0;i<7;i++){
                    char temp = in.next().charAt(0);
                    int index = temp;
                    inputFreq[index-'A']++;
                }
                break;
            case 1:
                System.out.println("Enter the 6 letters:");
                for(int i=0;i<6;i++){
                    char temp = in.next().charAt(0);
                    int index = temp;
                    inputFreq[index-'A']++;
                }
                break;
            case 2:
                System.out.println("Enter the 7 letters:");
                for(int i=0;i<7;i++){
                    char temp = in.next().charAt(0);
                    int index = temp;
                    inputFreq[index-'A']++;
                }
                System.out.println("Enter the number of existing letters:");
                int n = in.nextInt();
                System.out.println("Enter the "+n+" letter and its index:");
                for(int i=0;i<n;i++){
                    String temp = in.next();
                    char tmp = temp.charAt(0);
                    int index = in.nextInt();
                    constraints.put(index,tmp);
                }
                break;
            default:
                System.out.println("Enter valid case");
        }



        for(int i=0;i<26;i++){
            //System.out.print(inputFreq[i]+" ");
        }

        String filename = "C:\\Users\\nmalla\\Documents\\sowpods.txt";
        Scrabble test = new Scrabble();
        System.out.println(test.getScore(inputFreq,filename,caseNo,constraints));

    }
}
