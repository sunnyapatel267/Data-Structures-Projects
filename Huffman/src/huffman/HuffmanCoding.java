package huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import javax.lang.model.element.Element;

/**
 * This class contains methods which, when used together, perform the
 * entire Huffman Coding encoding and decoding process
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class HuffmanCoding {
    private String fileName;
    private ArrayList<CharFreq> sortedCharFreqList;
    private TreeNode huffmanRoot;
    private String[] encodings;

    /**
     * Constructor used by the driver, sets filename
     * DO NOT EDIT
     * @param f The file we want to encode
     */
    public HuffmanCoding(String f) { 
        fileName = f; 
    }

    /**
     * Reads from filename character by character, and sets sortedCharFreqList
     * to a new ArrayList of CharFreq objects with frequency > 0, sorted by frequency
     */
    public void makeSortedList() {
        StdIn.setFile(fileName);
        /* Your code goes here */

        ArrayList<Character> lettersInList = new ArrayList<Character>();
        do{

            lettersInList.add(StdIn.readChar());

        }while(StdIn.hasNextChar() != false);



        sortedCharFreqList = new ArrayList<CharFreq>();

        int num = 0;

        do{

            double countOfValue = 0; 
            double total = 0;
            double prob = 0;
            
            for(int i = 0; i < lettersInList.size();i++){
                if(lettersInList.get(num) == lettersInList.get(i)){
                    countOfValue++;
                }
                total++;
            }
            prob = countOfValue/total;

            char value = lettersInList.get(num);
            CharFreq e = new CharFreq(value, prob);

            int count1 = 0;
            for(int i = 0; i < lettersInList.size(); i++){
                if(lettersInList.get(1) == lettersInList.get(i)){
                    count1++;
                }
            }

            if(count1 == lettersInList.size()){
                if(sortedCharFreqList.isEmpty()){
                    CharFreq a = new CharFreq(value,prob);
                    CharFreq b = new CharFreq('b',0.00);
                    sortedCharFreqList.add(a);
                    sortedCharFreqList.add(b);
                }
            }else{

                int count = 0;

                if(sortedCharFreqList.isEmpty()){
                    
                    sortedCharFreqList.add(e);

                }else{
                    for(int i = 0; i < sortedCharFreqList.size();i++){
                        if(sortedCharFreqList.get(i).getCharacter() == e.getCharacter()){
                            count++;
                        }
                    }
                    if(count == 0){
                        sortedCharFreqList.add(e);
                    }

                }
            
            }
            num++;
            
            // runs till list is empty
        }while(num < lettersInList.size()); 

        Collections.sort(sortedCharFreqList);
        
    }

    /**
     * Uses sortedCharFreqList to build a huffman coding tree, and stores its root
     * in huffmanRoot
     */
    public void makeTree() {
        /* Your code goes here */
        Queue<TreeNode> tree = new Queue<TreeNode>();
        

        do{
            TreeNode t1 = new TreeNode();
            TreeNode t2 = new TreeNode();
            CharFreq bigTree = new CharFreq();
            huffmanRoot = new TreeNode();
            
            if(tree.isEmpty() == true){
                // This happens when the Tree(Queue) is empty during first iteration.
                
                t1 = new TreeNode(sortedCharFreqList.get(0), null, null);
                t2 = new TreeNode(sortedCharFreqList.get(1), null, null);

                sortedCharFreqList.remove(0);
                sortedCharFreqList.remove(0);

            }
            
            if(tree.isEmpty() == false){
                //This happens when Queue has 1 or more queue
                
                boolean yes1 = false;
                
                if((sortedCharFreqList.size() >= 2)){
                    if(sortedCharFreqList.get(0).getProbOcc() <= tree.peek().getData().getProbOcc()){
                        // When peak if source < target remove index(0)
                        t1 = new TreeNode(sortedCharFreqList.get(0), null, null);
                        
                        if(sortedCharFreqList.get(1).getProbOcc() <= tree.peek().getData().getProbOcc()){
                            //If index(1) < less tree.peek remove index
                            t2 = new TreeNode(sortedCharFreqList.get(1), null, null);
                            yes1 = true;
                        }else{
                            // else dequeue from the tree
                            t2 = tree.dequeue();
                        }

                        if(yes1 == true){
                            sortedCharFreqList.remove(0);
                            sortedCharFreqList.remove(0);
                        }else{
                            sortedCharFreqList.remove(0);
                        }

                    }else if(sortedCharFreqList.get(0).getProbOcc() > tree.peek().getData().getProbOcc()){
                        t1 = tree.dequeue();
                        if(sortedCharFreqList.get(0).getProbOcc() <= tree.peek().getData().getProbOcc()){
                            t2 = new TreeNode(sortedCharFreqList.get(0), null, null);
                            sortedCharFreqList.remove(0);
                        }else{
                            t2 = tree.dequeue();
                        }

                        
                    }

                }else if(sortedCharFreqList.size() == 1 && tree.size() > 1){
                    
                    if((sortedCharFreqList.get(0).getProbOcc() <= tree.peek().getData().getProbOcc())){
                            
                        t1 = new TreeNode(sortedCharFreqList.get(0), null, null);
                        t2 = tree.dequeue();
                        sortedCharFreqList.remove(0);

                        

                    }else if(sortedCharFreqList.get(0).getProbOcc() > tree.peek().getData().getProbOcc()){
                            
                            
                        t1 = tree.dequeue();
                        if(tree.size() >= 1){
                            if(sortedCharFreqList.get(0).getProbOcc() <= tree.peek().getData().getProbOcc()){
                                t2 = new TreeNode(sortedCharFreqList.get(0), null, null);
                                sortedCharFreqList.remove(0);
                            }else{
                                t2 = tree.dequeue();
                            }
                        }

                    }

                }else if(sortedCharFreqList.size() == 1 && tree.size() == 1){
                    if(sortedCharFreqList.get(0).getProbOcc() <= tree.peek().getData().getProbOcc()){
                        t1 = new TreeNode(sortedCharFreqList.get(0), null, null);
                        sortedCharFreqList.remove(0);
                        t2 = tree.dequeue();
                    
                    }else{
                        t1 = tree.dequeue();
                        t2 = new TreeNode(sortedCharFreqList.get(0), null, null);
                        sortedCharFreqList.remove(0);
                    }
                    
                    
                }else{
                    t1 = tree.dequeue();
                    t2 = tree.dequeue();
                }
                

            }
            
            //This sets the head treeNode
            
            bigTree.setCharacter(null);
            bigTree.setProbOcc(t1.getData().getProbOcc()+t2.getData().getProbOcc());
            huffmanRoot = new TreeNode(bigTree, t1, t2);
            tree.enqueue(huffmanRoot);
            

        }while((sortedCharFreqList.size() + tree.size()) > 1);


    }

    /**
     * Uses huffmanRoot to create a string array of size 128, where each
     * index in the array contains that ASCII character's bitstring encoding. Characters not
     * present in the huffman coding tree should have their spots in the array left null.
     * Set encodings to this array.
     */
    private String pOrder(TreeNode h, String x, char a){
        
        if(h == null) return "";
        if(h.getData().getCharacter() != null){
            if(h.getData().getCharacter() == a){
                
                return x;
            
            }
        }
        return pOrder(h.getLeft(), x+'0', a) + pOrder(h.getRight(), x+'1', a);

    }
    
    public void makeEncodings() {
        /* Your code goes here */
        encodings = new String[128];
        
        String x = "";
        for(int i = 0; i < encodings.length; i++){
            if(pOrder(huffmanRoot, x, (char)i).equals("")){
                encodings[i] = null;
            }else{
                encodings[i]= pOrder(huffmanRoot, x, (char)i);
            }
            
        }

    }

    /**
     * Using encodings and filename, this method makes use of the writeBitString method
     * to write the final encoding of 1's and 0's to the encoded file.
     * 
     * @param encodedFile The file name into which the text file is to be encoded
     */
    public void encode(String encodedFile) {
        StdIn.setFile(fileName);
        /* Your code goes here */
        String codeIsSecert = "";

        do{
            codeIsSecert += encodings[(int)StdIn.readChar()];
        }while(StdIn.hasNextChar() != false);

        writeBitString(encodedFile, codeIsSecert);

    }
    
    /**
     * Writes a given string of 1's and 0's to the given file byte by byte
     * and NOT as characters of 1 and 0 which take up 8 bits each
     * DO NOT EDIT
     * 
     * @param filename The file to write to (doesn't need to exist yet)
     * @param bitString The string of 1's and 0's to write to the file in bits
     */
    public static void writeBitString(String filename, String bitString) {
        byte[] bytes = new byte[bitString.length() / 8 + 1];
        int bytesIndex = 0, byteIndex = 0, currentByte = 0;

        // Pad the string with initial zeroes and then a one in order to bring
        // its length to a multiple of 8. When reading, the 1 signifies the
        // end of padding.
        int padding = 8 - (bitString.length() % 8);
        String pad = "";
        for (int i = 0; i < padding-1; i++) pad = pad + "0";
        pad = pad + "1";
        bitString = pad + bitString;

        // For every bit, add it to the right spot in the corresponding byte,
        // and store bytes in the array when finished
        for (char c : bitString.toCharArray()) {
            if (c != '1' && c != '0') {
                System.out.println("Invalid characters in bitstring");
                return;
            }

            if (c == '1') currentByte += 1 << (7-byteIndex);
            byteIndex++;
            
            if (byteIndex == 8) {
                bytes[bytesIndex] = (byte) currentByte;
                bytesIndex++;
                currentByte = 0;
                byteIndex = 0;
            }
        }
        
        // Write the array of bytes to the provided file
        try {
            FileOutputStream out = new FileOutputStream(filename);
            out.write(bytes);
            out.close();
        }
        catch(Exception e) {
            System.err.println("Error when writing to file!");
        }
    }

    /**
     * Using a given encoded file name, this method makes use of the readBitString method 
     * to convert the file into a bit string, then decodes the bit string using the 
     * tree, and writes it to a decoded file. 
     * 
     * @param encodedFile The file which has already been encoded by encode()
     * @param decodedFile The name of the new file we want to decode into
     */
    public void decode(String encodedFile, String decodedFile) {
        StdOut.setFile(decodedFile);
        /* Your code goes here */
        String message = readBitString(encodedFile);
        char[] messageChars = message.toCharArray();

        TreeNode x = huffmanRoot;
        for(int i = 0; i < messageChars.length; i++){
            if(messageChars[i] == '1'){
                x = x.getRight();
            }else{
                x = x.getLeft();
            }

            if(x.getData().getCharacter() != null){
                StdOut.print(x.getData().getCharacter());
                x = huffmanRoot;
            }
        }
        
        
        
    }

    /**
     * Reads a given file byte by byte, and returns a string of 1's and 0's
     * representing the bits in the file
     * DO NOT EDIT
     * 
     * @param filename The encoded file to read from
     * @return String of 1's and 0's representing the bits in the file
     */
    public static String readBitString(String filename) {
        String bitString = "";
        
        try {
            FileInputStream in = new FileInputStream(filename);
            File file = new File(filename);

            byte bytes[] = new byte[(int) file.length()];
            in.read(bytes);
            in.close();
            
            // For each byte read, convert it to a binary string of length 8 and add it
            // to the bit string
            for (byte b : bytes) {
                bitString = bitString + 
                String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            }

            // Detect the first 1 signifying the end of padding, then remove the first few
            // characters, including the 1
            for (int i = 0; i < 8; i++) {
                if (bitString.charAt(i) == '1') return bitString.substring(i+1);
            }
            
            return bitString.substring(8);
        }
        catch(Exception e) {
            System.out.println("Error while reading file!");
            return "";
        }
    }

    /*
     * Getters used by the driver. 
     * DO NOT EDIT or REMOVE
     */

    public String getFileName() { 
        return fileName; 
    }

    public ArrayList<CharFreq> getSortedCharFreqList() { 
        return sortedCharFreqList; 
    }

    public TreeNode getHuffmanRoot() { 
        return huffmanRoot; 
    }

    public String[] getEncodings() { 
        return encodings; 
    }
}