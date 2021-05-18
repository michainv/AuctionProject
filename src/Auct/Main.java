package Auct;

import java.io.*;



public class Main {
    public static void main(String[] args) {
        String fpath = "src\\input.txt";
        boolean export = true;
        if(args.length == 2){
            fpath = args[0];
            export = Boolean.parseBoolean(args[1]);
        }
        String res = procReq(fpath,export);
        System.out.println(res);
    }


    /**
     * @param fpath txt file containing the auction commands
     * @param export True if results are to be exported, or else return false.
     * @return A string containing all the auction results
     */
    public static String procReq(String fpath,boolean export) {
        String output = "";
        try {
            File file = new File(fpath);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            Auctioneer auct = new Auctioneer();
            while ((line = br.readLine()) != null) {
                AuctionRequest req = new AuctionRequest(line);
                auct.getUserRequest(req);
            }
            output = auct.exportAuctions();
            if (export){
                try {
                    FileWriter outp = new FileWriter("src\\output.txt");
                    outp.write(output);
                    outp.close();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
            return output;
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return output;
    }

}