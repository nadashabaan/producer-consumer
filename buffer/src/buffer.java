
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

class Q {

    ArrayList<Integer> num = new ArrayList<Integer>();
    public boolean valueSet = false, end = false, empty = false;
    int count = 0;
    public int number_of_prime = 0;
    public int Max_number = 0;
    public int n;
    public int buff;
    public String file_name;
    public static FileWriter myWriter;
    public File file;
    public static LocalTime runningTimeStart, runningTimeEnd;

    public Q(int n, int buff, String outputFile)  {
        this.n = n;
        this.buff = buff;
        this.file_name = outputFile;
        runningTimeStart = LocalTime.now();
        try {
            this.myWriter = new FileWriter(outputFile);
            this.file = new File(outputFile);
            this.file.createNewFile();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void put(int p, boolean en) {
        while (valueSet) {
            try {
                wait();
            } catch (Exception e) {
            }
        }
        if (p > 1) {
            this.num.add(p);
            String max= String.valueOf(p);
            Gui.setbigprime(max);
            number_of_prime++;
            String nums= String.valueOf(number_of_prime);
            Gui.setPrimeNums(nums);
            if (count == buff) {
                valueSet = true;
            } else if (p == 0) {
                end = true;
                valueSet = true;

            } else if (p == -1) {
                empty = true;
                valueSet = true;
                end = true;

            }
            count++;
            if (Max_number < p) {
                Max_number = p;

            }
        }

        if (en) {
            end = en;
            valueSet = true;
        }
        notify();
    }

    public synchronized void get() {
        while (!valueSet) {
            try {
                wait();
            } catch (Exception e) {
            }
        }
        count--;
        if (end && count == 0) {
            try {
                //FileWriter myWriter = new FileWriter(file_name);
                if (empty == true) {
                    myWriter.write("the Queue is Empty!\n");
                    System.out.println("# of elements (prime number)= " + number_of_prime);
                    System.out.println("Max_number= " + Max_number);


                } else {
                    myWriter.write(num + "\n");
                    System.out.println("# of elements (prime number)= " + number_of_prime);
                    System.out.println("Max_number= " + Max_number);
                }
                // runningTimeEnd = LocalTime.now();
                myWriter.close();
                runningTimeEnd = LocalTime.now();
                long time = Math.abs(runningTimeEnd.getSecond() - runningTimeStart.getSecond()) * 1000;
                System.out.println("time : " + time);
                String tmie0= String.valueOf(time);
                Gui.setTime(tmie0);
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();

            }
        }
        if (count == 0)
            valueSet = false;
        notify();
    }
}
//  public synchronized Integer get_number() {
//	  if(end)
//	  return( number_of_prime);
//	return number_of_prime;
//		  
//  }
//  public synchronized Integer get_max() {
//	  if(end)
//		  return( num.get(0));
//	return Max_number;
//		  
//  }


class Producer implements Runnable {
    Q q;

    public Producer(Q q) {
        this.q = q;
//this.n=n;
//this.buff=buff;
        Thread t = new Thread(this, "Producer");
        t.start();
    }

    public void run() {
        boolean end = false;
        int temp = 2;
        if (q.n <= 1) {
            q.put(-1, true);
            try {
                Thread.sleep(5);
            } catch (Exception e) {
            }
        } else {
            while (temp <= q.n) {
                boolean is_prime = true;
                {
                    for (int i = 2; i <= temp / 2; i++)
                        if ((temp % i) == 0) {
                            is_prime = false;
                            break;
                        }
                }
                if (is_prime && temp >= q.n)
                    q.put(temp, true);
                else if (is_prime)
                    q.put(temp, end);
                else if (temp >= q.n)
                    q.put(0, true);
                temp++;
                try {
                    Thread.sleep(5);
                } catch (Exception e) {
                }
            }
        }
    }
}

class Consumer implements Runnable {
    Q q;

    public Consumer(Q q) {

        this.q = q;
        Thread t = new Thread(this, "Consumer");
        t.start();
    }

    public void run() {
        while (true) {
            q.get();
            try {
                Thread.sleep(5);
            } catch (Exception e) {
            }
        }
    }
}

public class buffer {
    public static void main(String[] args) throws IOException {
        Gui gui=new Gui();
//        int n = 1000, buff = 8;
//        String outfile = "test.txt";
//        Q q = new Q(n, buff, outfile);
//        new Producer(q);
//        new Consumer(q);

//System.out.println("num  "+ q.get_number()) ;
//System.out.println( "max  "+q.get_max() );
    }

}