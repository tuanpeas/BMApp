package view;

import controller.DataController;
import model.Book;
import model.BookReaderManagement;
import model.Reader;

import java.util.ArrayList;
import java.util.Scanner;

public class View {
    public static void main(String[] args) {
        int choice = 0;
        var booksFileName = "BOOK.DAT";
        var readersFileName = "READER.DAT";
        var brmFileName= "BRM.DAT";

        var controller = new DataController();

        var books = new ArrayList<Book>();
        var readers = new ArrayList<Reader>();
        var brms = new ArrayList<BookReaderManagement>();

        var isReaderChecked = false;
        var isBookChecked = false;


        Scanner scanner = new Scanner((System.in));
        do{
            System.out.println("___________MENU___________");
            System.out.println("1. ファイルに本を追加します。");
            System.out.println("2. ファイルの本を表示します。");
            System.out.println("3. ファイルに読者を追加します。");
            System.out.println("4. 読者のリストを表示します。");
            System.out.println("5. 拝借の本の管理情報を作ります。");
            System.out.println("0. アプリケーションを終了します。");
            System.out.println("選択します。");

            choice = scanner.nextInt();
            scanner.nextLine(); // doc bo dong chua lua chon

            switch (choice){
                case 0:
                    System.out.println("---------------------------------------");
                    System.out.println("ありがとうございました");
                    break;

                case 1:
                    if (!isBookChecked) {
                        checkBookID(controller, booksFileName);
                        isBookChecked = true;
                    }

                    String[] specs = {"Science", "Art", "Economic", "IT"};

                    String bookName, author, spec;
                    int year, quan, sp;
                    System.out.println("本の名前を記入します。");
                    bookName = scanner.nextLine();

                    System.out.println("作者の名前を記入します。");
                    author = scanner.nextLine();

                    do {
                        System.out.println("カテゴリーを記入します。");
                        System.out.println("1. science. \n2.Art. \n3. Economic. \n4. IT.");
                        sp = scanner.nextInt();
                    }while (sp < 1 || sp > 4);

                    spec = specs[sp - 1];

                    System.out.println("出版年を入力します。");
                    year =scanner.nextInt();

                    System.out.println("数量を記入します。");
                    quan = scanner.nextInt();
                    //public Book(int bookID, String bookName, String author,
                    // String specialization, int publishYear, int quantity)

                    Book book = new Book(0,bookName, author,spec,year,quan);
                    controller.writeBookToFile(book, booksFileName);
                    break;

                case 2:
                    books = controller.readBooksFromFile(booksFileName);
                    showBookInfo(books);
                    break;

                case 3:
                    if (isReaderChecked){
                        checkReaderID(controller, readersFileName);
                        isReaderChecked = true;
                    }
                    String fullName, address, phoneNum;
                    System.out.println("指名を記入します。");
                    fullName = scanner.nextLine();

                    System.out.println("住所を記入地ます。");
                    address = scanner.nextLine();

                    do{
                        System.out.println("携帯番号を記入します。");
                        phoneNum = scanner.nextLine();
                    }while (!phoneNum.matches("\\d{10}"));
                    //public Reader(int readerID, String fullName,
                    // String address, String phoneNumber)
                    Reader reader = new Reader(0, fullName, address, phoneNum);
                    controller.writeReaderToFile(reader, readersFileName);
                    break;

                case 4:
                    readers = controller.readReadersFromFile(readersFileName);
                    showReaderInfo(readers);
                    break;

                case 5:
                    //B0 : khoi tao ds
                    readers = controller.readReadersFromFile(readersFileName);
                    books = controller.readBooksFromFile(booksFileName);
                    brms = controller.readBRMsFromFile(brmFileName);

                    int readerID, booksID;
                    boolean isBorrowable = false;
                    do {
                        showReaderInfo(readers);
                        System.out.println("--------------------------------");
                        System.out.println("読者のIDを記入します: ");
                        readerID = scanner.nextInt();
                        if(readerID == 0){
                            break;//tat ca ban doc da duowc muon du sach qui dinh
                        }
                        isBorrowable = checkBorrowed(brms, readerID);
                        if (isBorrowable){
                            break;
                        }else {
                            System.out.println("読者が借りすぎました。");
                        }
                    }while (true);
                    break;
            }

        }while (choice !=0);
    }

    private static boolean checkBorrowed(ArrayList<BookReaderManagement> brms, int readerID) {
        int count = 0;
        for (var r : brms){
            if(r.getReader().getReaderID() == readerID){
                count += r.getNumOfBorrow();
            }
        }
        if (count == 15){
            return  false;//khong muon duoc
        }
        return true;// duoc phep muon
    }

    private static void checkReaderID(DataController controller, String readersFileName) {
        var readers = controller.readReadersFromFile(readersFileName);
        if (readers.size() == 0){
            //do nothing
        }else{
            Reader.setId(readers.get(readers.size() -1 ).getReaderID() +1);
        }
    }

    private static void showReaderInfo(ArrayList<Reader> readers) {
        System.out.println("ファイルに読者の情報");
        for (var r: readers) {
            System.out.println(r);
        }

    }

    private static void checkBookID(DataController controller, String fileName) {
        var listBooks = controller.readBooksFromFile(fileName);
        if (listBooks.size() == 0){
            //do nothing
        }else{
            Book.setId(listBooks.get(listBooks.size() - 1).getBookID()+1);
        }
    }

    private static void showBookInfo(ArrayList<Book> books) {
        System.out.println("本の情報を出ます。");
        for (var b : books){
            System.out.println(b);
        }
    }

}
