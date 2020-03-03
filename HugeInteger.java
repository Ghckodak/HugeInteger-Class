import java.util.Random;


public class HugeInteger{

    private int digit;
    private int[] eachNumber;

    /*********** Constructor ****************/

    public HugeInteger(String val) throws IllegalArgumentException {
        digit=val.length();
        
        int error=0;
        //Check if the string contains something that are not allowed
        if (val.charAt(0)=='-'){
            for (int i=1;i<digit;i++){
                if (val.charAt(i)>=48&&val.charAt(i)<=57){
                    error=0;
                }
                else{
                    error=1;
                    throw new IllegalArgumentException("Character in the string are not allowed");  
                }
            }
        }
        else if (val.charAt(0)>=48&&val.charAt(0)<=57){
            for (int i=1;i<digit;i++){
                if (val.charAt(i)>=48&&val.charAt(i)<=57){
                    error=0;
                }
                else{
                    error=1;
                    throw new IllegalArgumentException("Character in the string are not allowed");  
                }
            }
        }
        else{
            error=1;
            throw new IllegalArgumentException("Character in the string are not allowed");
        }
                

        //proceed when there is no error
        if (error==0){
            
            //if negative
            if (val.charAt(0)=='-'){
                if(digit==2){
                    eachNumber=new int[1];
                    digit=digit-1;
                    eachNumber[0]=0-(val.charAt(1)-48);
                }
                else{
                    eachNumber=new int[digit-1];
                    for (int i=2;i<digit;i++){
                        eachNumber[0]=0-(val.charAt(1)-48);
                        eachNumber[i-1]=val.charAt(i)-48; //Read numbers in the string and store them in the array "eachNumber"
                    }
                    digit=digit-1;
                }
                
            }
            //if positive
            if (val.charAt(0)!='-'){
                eachNumber=new int[digit];
                for (int i=0;i<digit;i++){
                    eachNumber[i]=val.charAt(i)-48; //Read numbers in the string and store them in the array "eachNumber"
                }
            }
        }   
    }

    public HugeInteger(int n) throws IllegalArgumentException{
        digit=n;
        eachNumber=new int[digit];

        // throw expection if n<1
        if (n<1){
            throw new IllegalArgumentException("Digit cannot be negative");
        }
        if (n>=1){
            for (int i=1;i<digit;i++){
                Random randomNumber=new Random();
                eachNumber[0]=randomNumber.nextInt(8)+1;
                eachNumber[i]=randomNumber.nextInt(9);  //generate a random number from 0~9
            }
        }
    }

    /*************** Method *******************/  
    

    public String toString(){
        StringBuilder result=new StringBuilder();
    
        for (int i=0;i<digit;i++){
            result.append(eachNumber[i]);
            
        }
        int length=result.length();
        int initialLen=result.length();
        if (result.charAt(0)==48){
            int n=0;
            while (n<initialLen&&result.charAt(n)==48){
                length=length-1;
                if (length==0){
                    break;
                }
                result.deleteCharAt(n);
                
            }
            result.setLength(length);
        }
        if (length==0){
            result.append("0");
        }

        return result.toString();
    }


    public HugeInteger add(HugeInteger h){
        //initalize sum
        String stringTemp="";
        int length=0;
        boolean selfNegative=false;
        boolean thatNegative=false;
        HugeInteger sum=null;
        StringBuilder self=new StringBuilder();
        StringBuilder that=new StringBuilder();
        self.append(this.toString());
        that.append(h.toString());

        //check if their digits are equal, if not, fill leftmost digit with 0
        
        if (self.charAt(0)==45){
            selfNegative=true;
            StringBuilder temp=self;
            self=that;
            that=temp;
            self.deleteCharAt(0);

        }
        if (that.charAt(0)==45){
            thatNegative=true;
            that.deleteCharAt(0);

        }
        

        if (selfNegative^thatNegative){
            HugeInteger self1=new HugeInteger(self.toString());
            HugeInteger that1=new HugeInteger(that.toString());
            selfNegative=false;
            thatNegative=false;
            
            sum=new HugeInteger(self1.substract(that1).toString());

            return sum;
            
        }

        if(selfNegative&&thatNegative){
            HugeInteger self2=new HugeInteger("-"+self.toString());
            HugeInteger that2=new HugeInteger(that.toString());
            selfNegative=true;
            thatNegative=false;
            sum=new HugeInteger(self2.substract(that2).toString());
            return sum;

        }



        //Now they have equal length, ready to add 
        else{

            if (self.length()<=that.length()){
                int diff=that.length()-self.length(); 
                for(int i=0;i<diff;i++){
                    self.insert(0, 0);  
                }
                length=that.length();
            }
    
            if (self.length()>that.length()){
                int diff=self.length()-that.length();    
                for(int i=0;i<diff;i++){
                    that.insert(0, 0); 
                }
                length=self.length();
            }
    
            int carry=0;
            for (int i=length-1;i>=0;i--){
                int temp=0;
                temp=self.charAt(i)-48+that.charAt(i)-48+carry;
                if (temp>=10){
                    temp=temp-10;
                    carry=1;
                }
                else if (temp<10){
                    carry=0;
                }

                stringTemp=Integer.toString(temp)+stringTemp;
            }

            //If still have carry, set carry to the first digit
            if (carry==1){
                stringTemp=Integer.toString(carry)+stringTemp;
            }

            sum=new HugeInteger(stringTemp);
        }
        return sum;
    }





    public HugeInteger substract(HugeInteger h){    
        String stringTemp="";
        int length=0;
        boolean selfNegative=false;
        boolean thatNegative=false;
        boolean allNeagative=false;
        boolean isReverse=false; 
        HugeInteger res=null;
        StringBuilder self=new StringBuilder();
        StringBuilder that=new StringBuilder();
        self.append(this.toString());
        that.append(h.toString());
        //Cheak if they are negative

        if (self.charAt(0)==48){     
            res=new HugeInteger(that.toString());
            return res;
        }

        if(that.charAt(0)==48){
            res=new HugeInteger(self.toString());
            return res;
        }


        if (self.charAt(0)==45){
            selfNegative=true;
            self.deleteCharAt(0);

        }
        if (that.charAt(0)==45){
            thatNegative=true;
            if (selfNegative){
                allNeagative=true;
            }
            that.deleteCharAt(0);
        }

        if (selfNegative^thatNegative){
            HugeInteger self1=new HugeInteger(self.toString());
            HugeInteger that1=new HugeInteger(that.toString());
            String sum=self1.add(that1).toString();
            if (self1.compareTo(that1)==-1){
                sum="-"+sum;
            }
            
            res=new HugeInteger(sum);
            return res;
        }

        //check if their digits are equal, if not, fill leftmost digit with 0
        if ((selfNegative^thatNegative)==false){
            if (self.length()<that.length()){
                int diff=that.length()-self.length(); 
                for(int i=0;i<diff;i++){
                    self.insert(0, 0);  
                }
                StringBuilder temp1=self;
                self=that;
                that=temp1;
                isReverse=true;
                length=that.length();
            }

            if (self.length()>=that.length()){
                int diff=self.length()-that.length();    
                for(int i=0;i<diff;i++){
                    that.insert(0, 0); 
                }
                length=self.length();
            }

            //Now they have equal length, ready to substract

        
            int borrow=0;
    
            for (int i=length-1;i>=0;i--){
                int temp=0;
                temp=(self.charAt(i)-48)-(that.charAt(i)-48)-borrow;

                if ((i!=0)&&(temp>=0)){
                    borrow=0;
                }
                else if ((i>0)&&(temp<0)){
                    temp=temp+10;
                    borrow=1;
                }
                stringTemp=Integer.toString(temp)+stringTemp;
            }
            if (isReverse||allNeagative){
                stringTemp="-"+stringTemp;
            }
            res=new HugeInteger(stringTemp);
        }

        return res;
    }
        
    

    public int compareTo(HugeInteger h){
        int result;
        if (this.substract(h).toString().charAt(0)==45){
            result=-1;
        }
        else if  (this.substract(h).toString().charAt(0)==48){
            result=0;
        }
        else{
            result=1;
        }
        return result;
    }

    public HugeInteger multiply(HugeInteger h){
        StringBuilder self=new StringBuilder();
        StringBuilder that=new StringBuilder();
        boolean selfNegative=false;
        boolean thatNegative=false;
        self.append(this.toString());
        that.append(h.toString());

        if (self.charAt(0)==45){
            selfNegative=true;
            self.deleteCharAt(0);

        }
        if (that.charAt(0)==45){
            thatNegative=true;
            that.deleteCharAt(0);
        }
        // size of resulting HugeInteger
        int length = self.length()+that.length();
//        int j = h1.getSize(), k = h2.getSize();
        int[] product = new int[length];
        int i=0;
        int pos=length-1;
        int carry = 0;
        int currentProduct= 0;
        // initialize product array
        for(int m=that.length()-1;m>=0;m--){
            i=pos;
            pos=pos-1;
            for(int n=(self.length()-1);n>=0;n--){
                currentProduct=product[i]+((self.charAt(n)-48)*(that.charAt(m)-48)+carry);
                product[i]=currentProduct%10;
                carry=currentProduct/10;
                i--;
            }
            product[i]=carry;
            carry=0;
        }
        StringBuilder result=new StringBuilder();
        for(i=0;i<length;i++)
        {
            result.append(product[i]);
        }

        if (selfNegative^thatNegative){
            return new HugeInteger("-"+result.toString());
        }
        return new HugeInteger(result.toString());
    }
}






