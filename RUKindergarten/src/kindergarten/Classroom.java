package kindergarten;
/**
 * This class represents a Classroom, with:
 * - an SNode instance variable for students in line,
 * - an SNode instance variable for musical chairs, pointing to the last student in the list,
 * - a boolean array for seating availability (eg. can a student sit in a given seat), and
 * - a Student array parallel to seatingAvailability to show students filed into seats 
 * --- (more formally, seatingAvailability[i][j] also refers to the same seat in studentsSitting[i][j])
 * 
 * @author Ethan Chou
 * @author Kal Pandit
 * @author Maksims Kurjanovics Kravcenko
 */
public class Classroom {
    private SNode studentsInLine;             // when students are in line: references the FIRST student in the LL
    private SNode musicalChairs;              // when students are in musical chairs: references the LAST student in the CLL
    private boolean[][] seatingAvailability;  // represents the classroom seats that are available to students
    private Student[][] studentsSitting;      // when students are sitting in the classroom: contains the students

    /**
     * Constructor for classrooms. Do not edit.
     * @param l passes in students in line
     * @param m passes in musical chairs
     * @param a passes in availability
     * @param s passes in students sitting
     */
    public Classroom ( SNode l, SNode m, boolean[][] a, Student[][] s ) {
        studentsInLine      = l;
        musicalChairs       = m;
        seatingAvailability = a;
        studentsSitting     = s;
    }
    /**
     * Default constructor starts an empty classroom. Do not edit.
     */
    public Classroom() {
        this(null, null, null, null);
    }

    /**
     * This method simulates students coming into the classroom and standing in line.
     * 
     * Reads students from input file and inserts these students in alphabetical 
     * order to studentsInLine singly linked list.
     * 
     * Input file has:
     * 1) one line containing an integer representing the number of students in the file, say x
     * 2) x lines containing one student per line. Each line has the following student 
     * information separated by spaces: FirstName LastName Height
     * 
     * @param filename the student information input file
     */
    public void makeClassroom ( String filename ) {

        // WRITE YOUR CODE HERE

        // Read File and # of students
        StdIn.setFile(filename);
        int size = StdIn.readInt();

        

        // For loop runs "size" amount of times
        for(int i = 0; i < size; i++){
            
            //Reads and Adds to Student Object
            String f1 = StdIn.readString();
            String l1 = StdIn.readString();
            int h1 = StdIn.readInt();
            Student s1 = new Student(f1, l1, h1);


            //Add to Front of LL
            if(i == 0){
                // Adds the first stuent line when i == 0
                SNode student1 = new SNode();
                student1.setStudent(s1);
                student1.setNext(studentsInLine);
                studentsInLine = student1;
            } 

            if(i > 0){
                // happens when i > 0
                SNode x = studentsInLine;
                // Starts Node at front

                //Runs till End of Linked List
                while(x != null){

                    //s1 Comes before x in the Linked List
                    if(s1.compareNameTo(x.getStudent()) < 0){
                        //This adds to the front of the LL
                        SNode second = studentsInLine; // makes prev first node to seconf
                        studentsInLine = new SNode(); // creates new node in front
                        studentsInLine.setStudent(s1); // sets student
                        studentsInLine.setNext(second); // sets next to second
                        break;
                    }
                    
                    if(s1.compareNameTo(x.getStudent()) > 0){
                        // Adds after x
                        if(x.getNext() != null){
                            // If next node not null

                            // Compare with x.next node
                            if(s1.compareNameTo(x.getNext().getStudent()) < 0){
                                // Adds to after x but before x.next
                                SNode b = new SNode();
                                b.setStudent(s1);
                                b.setNext(x.getNext());
                                x.setNext(b);
                                b = x;
                                break;
                            }
                        }else{
                             
                            //Else add after x
                            SNode b = new SNode();
                            b.setStudent(s1);
                            b.setNext(null);
                            x.setNext(b);
                            b = x;
                            break;
                        }

                    }

                    //Moves to next Node
                    x = x.getNext();
                }
            }

            
        }
        
        
    }

    /**
     * 
     * This method creates and initializes the seatingAvailability (2D array) of 
     * available seats inside the classroom. Imagine that unavailable seats are broken and cannot be used.
     * 
     * Reads seating chart input file with the format:
     * An integer representing the number of rows in the classroom, say r
     * An integer representing the number of columns in the classroom, say c
     * Number of r lines, each containing c true or false values (true denotes an available seat)
     *  
     * This method also creates the studentsSitting array with the same number of
     * rows and columns as the seatingAvailability array
     * 
     * This method does not seat students on the seats.
     * 
     * @param seatingChart the seating chart input file
     */
    public void setupSeats(String seatingChart) {
    // WRITE YOUR CODE HERE

        StdIn.setFile(seatingChart);
        int r = StdIn.readInt();
        int c = StdIn.readInt();
        seatingAvailability = new boolean[r][c];
        studentsSitting = new Student[r][c];
        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                seatingAvailability[i][j] = StdIn.readBoolean();
            }
        }

    }

    /**
     * 
     * This method simulates students taking their seats in the classroom.
     * 
     * 1. seats any remaining students from the musicalChairs starting from the front of the list
     * 2. starting from the front of the studentsInLine singly linked list
     * 3. removes one student at a time from the list and inserts them into studentsSitting according to
     *    seatingAvailability
     * 
     * studentsInLine will then be empty
     */
    public void seatStudents () {
    // WRITE YOUR CODE HERE
        //SNode ptr = studentsInLine;
        

        for(int i = 0; i < studentsSitting.length; i++){
            for(int j = 0; j < studentsSitting[0].length; j++){
                if(seatingAvailability[i][j] == true){
                    if(musicalChairs != null){
                        //Leave seat open for musical chair winner
                        studentsSitting[i][j] = musicalChairs.getStudent();
                        musicalChairs = null;
                    }else{ 
                        if(studentsInLine != null){
                            studentsSitting[i][j] = studentsInLine.getStudent();
                            studentsInLine.setStudent(null);
                            studentsInLine = studentsInLine.getNext();
                        }
                    }

                    //count++;
                }
            }
        }
    
    }

    /**
     * Traverses studentsSitting row-wise (starting at row 0) removing a seated
     * student and adding that student to the end of the musicalChairs list.
     * 
     * row-wise: starts at index [0][0] traverses the entire first row and then moves
     * into second row.
     */
    public void insertMusicalChairs () {
        
        // WRITE YOUR CODE HERE
        
        for(int i = 0; i < studentsSitting.length; i++){
            for(int j = 0; j < studentsSitting[0].length; j++){
                if(studentsSitting[i][j] != null){
                    if(musicalChairs == null){                        
                        SNode newLast = new SNode();
                        newLast.setStudent(studentsSitting[i][j]); //intialize newLast

                        newLast.setNext(newLast); // set next equal to itself
                        musicalChairs = newLast; //changes musicalChairs to be Last
                        
                        studentsSitting[i][j] = null; //Removes student from line
                        
                        
                    }else{
                        //Create new Node
                        SNode newLast = new SNode();
                        newLast.setStudent(studentsSitting[i][j]); //intializes newLast

                        newLast.setNext(musicalChairs.getNext()); // newLast next = to musicalChairs to next(bc it points to itself)
                        musicalChairs.setNext(newLast); // Musical chairs points to newLast
                        musicalChairs = newLast;// Update Last Node

                        studentsSitting[i][j] = null; // Remove student from line
                        
                        
                    }
                }
                

            }
        }

     }

    /**
     * 
     * This method repeatedly removes students from the musicalChairs until there is only one
     * student (the winner).
     * 
     * Choose a student to be elimnated from the musicalChairs using StdRandom.uniform(int b),
     * where b is the number of students in the musicalChairs. 0 is the first student in the 
     * list, b-1 is the last.
     * 
     * Removes eliminated student from the list and inserts students back in studentsInLine 
     * in ascending height order (shortest to tallest).
     * 
     * The last line of this method calls the seatStudents() method so that students can be seated.
     */

    private int findSizeOfLL(){
        int count = 0;
        SNode j = musicalChairs.getNext();
        while(j != musicalChairs && j != null){            
            count++;
            j = j.getNext();
        }
        if(j == musicalChairs){
            count++;
        }
        
        
        return count;
    }

    private void addStudentToLine(Student s){
        SNode x = studentsInLine;
        
        if(studentsInLine == null){
            SNode student1 = new SNode();
            student1.setStudent(s);
            student1.setNext(studentsInLine);
            studentsInLine = student1;
        }

        if(studentsInLine != null){
           while(x != null){
                // If student is short than others in Linked List
                if(s.getHeight() < x.getStudent().getHeight()){
                    
                    SNode second = studentsInLine;
                    studentsInLine = new SNode(); // creates new node in front
                    studentsInLine.setStudent(s); // sets student
                    studentsInLine.setNext(second); // sets next to second
                    break;
                }

                //If students is taller than other in LL
                if(s.getHeight() >= x.getStudent().getHeight()){
                    if(x.getNext() != null){
                        if(s.getHeight() <= x.getNext().getStudent().getHeight()){
                            // Add between x and x.next    
                            SNode b = new SNode();
                            b.setStudent(s);
                            b.setNext(x.getNext());
                            x.setNext(b);
                            b = x;
                            break;
                                
                        }
                    }else{
                        // Adds at end
                        SNode b = new SNode();
                        b.setStudent(s);
                        b.setNext(x.getNext());
                        x.setNext(b);
                        b = x;
                        break;
                    }
                }

                x = x.getNext();
            }
        }
        
        
    }

    private void removeStudent(){
        
        int rand = StdRandom.uniform(findSizeOfLL());
        
        int count = 0;
        
        SNode ptr = musicalChairs.getNext();
        SNode prev = musicalChairs;
        do{

            if(count == rand){
                break;
            }
            prev = ptr;
            ptr = ptr.getNext();
            count++;

        }while(ptr != musicalChairs.getNext());

        // Adds to  studentsInLine LL

        addStudentToLine(ptr.getStudent());

        
        // Removes student from musical chairs
        if(ptr == musicalChairs.getNext()){
            prev.setNext(ptr.getNext());
            
        }else if(ptr == musicalChairs){
            prev.setNext(ptr.getNext());
            musicalChairs = prev;
        }else{
            prev.setNext(ptr.getNext());
        }

        
    }

    public void playMusicalChairs() {
        
        
        
        do{
            removeStudent();
            
        }while(findSizeOfLL() != 1);

        seatStudents();
        
        

    } 

    /**
     * Insert a student to wherever the students are at (ie. whatever activity is not empty)
     * Note: adds to the end of either linked list or the next available empty seat
     * @param firstName the first name
     * @param lastName the last name
     * @param height the height of the student
     */
    public void addLateStudent ( String firstName, String lastName, int height ) {
        
        // WRITE YOUR CODE HERE

        Student s2 = new Student(firstName, lastName, height);

        if(studentsInLine != null){ 
            // Students in LL studentsInLine
            SNode x = studentsInLine;
            do{
                //Traverse to the end of LL
                x = x.getNext();
                if(x.getNext() == null){
                    SNode a = new SNode();
                    a.setStudent(s2);
                    x.setNext(a);
                    a.setNext(null);
                    a = x;
                    break;
                }

            }while(x != null);


        }else if(musicalChairs != null){ 
            // Students in CLL musicalChairs
            SNode newLast = new SNode();
            newLast.setStudent(s2);

            newLast.setNext(musicalChairs.getNext());
            musicalChairs.setNext(newLast);
            musicalChairs = newLast;

        }else{ 
            // students in students siting
            boolean finished = false; 
            for(int i = 0; i < studentsSitting.length && !finished; i++){
                for(int j = 0; j < studentsSitting[0].length; j++){
                    if(seatingAvailability[i][j] == true){
                        if(studentsSitting[i][j] == null){
                            finished = true;
                            studentsSitting[i][j] = s2;
                            break;
                        }
                        
                    }
                }
                
            }
        }
        
    }

    /**
     * A student decides to leave early
     * This method deletes an early-leaving student from wherever the students 
     * are at (ie. whatever activity is not empty)
     * 
     * Assume the student's name is unique
     * 
     * @param firstName the student's first name
     * @param lastName the student's last name
     */
    public void deleteLeavingStudent ( String firstName, String lastName ) {

        // WRITE YOUR CODE HERE

        if(studentsInLine != null){
            // All kids in Line
            SNode prev = null;
            SNode ptr = studentsInLine;

            while(ptr != null){
                if(ptr.getStudent().getFirstName().equals(firstName) && ptr.getStudent().getLastName().equals(lastName)){
                    
                    break;
                }
                prev = ptr;
                ptr = ptr.getNext();
            }

            if(ptr == studentsInLine){
                studentsInLine = studentsInLine.getNext();
            }else{
                prev.setNext(ptr.getNext());
            }

        }else if(musicalChairs != null){
            // All in Musical Chairs
            SNode ptr = musicalChairs.getNext();
            SNode prev = musicalChairs;
            do{
                
                if(ptr.getStudent().getFirstName().equals(firstName) && ptr.getStudent().getLastName().equals(lastName)){
                    break;
                }
                prev = ptr;
                ptr = ptr.getNext();

            }while(ptr != musicalChairs.getNext());

            // Removes student from musical chairs
            if(ptr == musicalChairs.getNext()){
                prev.setNext(ptr.getNext());
            
            }else if(ptr == musicalChairs){
                prev.setNext(ptr.getNext());
                musicalChairs = prev;
            }else{
                prev.setNext(ptr.getNext());
            }

        }else{
            //Students in Seat
            for(int i = 0; i < studentsSitting.length; i++){
                for(int j = 0; j < studentsSitting[0].length; j++){
                    if(studentsSitting[i][j] != null){
                        if(studentsSitting[i][j].getFirstName().equals(firstName) && studentsSitting[i][j].getLastName().equals(lastName)){
                            studentsSitting[i][j] = null;
                        }
                    }
                } 
            } 

        }

    }

    /**
     * Used by driver to display students in line
     * DO NOT edit.
     */
    public void printStudentsInLine () {

        //Print studentsInLine
        StdOut.println ( "Students in Line:" );
        if ( studentsInLine == null ) { StdOut.println("EMPTY"); }

        for ( SNode ptr = studentsInLine; ptr != null; ptr = ptr.getNext() ) {
            StdOut.print ( ptr.getStudent().print() );
            if ( ptr.getNext() != null ) { StdOut.print ( " -> " ); }
        }
        StdOut.println();
        StdOut.println();
    }

    /**
     * Prints the seated students; can use this method to debug.
     * DO NOT edit.
     */
    public void printSeatedStudents () {

        StdOut.println("Sitting Students:");

        if ( studentsSitting != null ) {
        
            for ( int i = 0; i < studentsSitting.length; i++ ) {
                for ( int j = 0; j < studentsSitting[i].length; j++ ) {

                    String stringToPrint = "";
                    if ( studentsSitting[i][j] == null ) {

                        if (seatingAvailability[i][j] == false) {stringToPrint = "X";}
                        else { stringToPrint = "EMPTY"; }

                    } else { stringToPrint = studentsSitting[i][j].print();}

                    StdOut.print ( stringToPrint );
                    
                    for ( int o = 0; o < (10 - stringToPrint.length()); o++ ) {
                        StdOut.print (" ");
                    }
                }
                StdOut.println();
            }
        } else {
            StdOut.println("EMPTY");
        }
        StdOut.println();
    }

    /**
     * Prints the musical chairs; can use this method to debug.
     * DO NOT edit.
     */
    public void printMusicalChairs () {
        StdOut.println ( "Students in Musical Chairs:" );

        if ( musicalChairs == null ) {
            StdOut.println("EMPTY");
            StdOut.println();
            return;
        }
        SNode ptr;
        for ( ptr = musicalChairs.getNext(); ptr != musicalChairs; ptr = ptr.getNext() ) {
            StdOut.print(ptr.getStudent().print() + " -> ");
        }
        if ( ptr == musicalChairs) {
            StdOut.print(musicalChairs.getStudent().print() + " - POINTS TO FRONT");
        }
        StdOut.println();
    }

    /**
     * Prints the state of the classroom; can use this method to debug.
     * DO NOT edit.
     */
    public void printClassroom() {
        printStudentsInLine();
        printSeatedStudents();
        printMusicalChairs();
    }

    /**
     * Used to get and set objects.
     * DO NOT edit.
     */

    public SNode getStudentsInLine() { return studentsInLine; }
    public void setStudentsInLine(SNode l) { studentsInLine = l; }

    public SNode getMusicalChairs() { return musicalChairs; }
    public void setMusicalChairs(SNode m) { musicalChairs = m; }

    public boolean[][] getSeatingAvailability() { return seatingAvailability; }
    public void setSeatingAvailability(boolean[][] a) { seatingAvailability = a; }

    public Student[][] getStudentsSitting() { return studentsSitting; }
    public void setStudentsSitting(Student[][] s) { studentsSitting = s; }

}