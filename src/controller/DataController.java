package controller;

import model.Book;
import model.BookReaderManagement;
import model.Reader;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class DataController {
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;
    private PrintWriter printWriter;
    private Scanner scanner;

    public void openFileToWrite(String fileName){
        try {
            fileWriter = new FileWriter(fileName, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            printWriter = new PrintWriter(bufferedWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void openFiletoRead(String fileName){
        try{
            File file = new File(fileName);
            if (!file.exists()){
                file.createNewFile();
            }
            scanner = new Scanner(Paths.get(fileName),"UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void closeFileAfterRead(String fileName){
        try {
            scanner.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void writeBookToFile(Book book, String fileName){
        openFileToWrite(fileName);
        printWriter.println(book.getBookID()+"|"
                +book.getBookName()+"|"+book.getAuthor()+"|"+book.getSpecialization()+"|"
                + book.getPublishYear()+"|"+book.getQuantity());
        closeFileAfterWrite(fileName);
    }

    public void writeReaderToFile(Reader reader, String fileName){
        openFileToWrite(fileName);
        printWriter.println(reader.getReaderID()+"|"
                +reader.getFullName()+"|"+reader.getAddress()+"|"+reader.getPhoneNumber());
        closeFileAfterWrite(fileName);
    }

    public void writeBRMToFile(BookReaderManagement brm, String fileName){
        openFileToWrite(fileName);
        printWriter.println(brm.getReader().getReaderID()+"|"
                +brm.getBook().getBookID()+"|"+brm.getNumOfBorrow()+"|"+brm.getState());
        closeFileAfterWrite(fileName);
    }

    public void closeFileAfterWrite(String fileName){
        try{
            printWriter.close();
            bufferedWriter.close();
            fileWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        };
    }

    public ArrayList<Reader> readReadersFromFile(String fileName){
        openFiletoRead(fileName);
        ArrayList<Reader> readers = new ArrayList<>();
        while (scanner.hasNextLine()){
            String data = scanner.nextLine();
            Reader reader = createReaderFromData(data);
            readers.add(reader);
        }
        closeFileAfterRead(fileName);
        return  readers;
    }


    public Reader createReaderFromData(String data) {
        String[] datas = data.split("\\|");
        //reader.getReaderID()+"|"
        //                +reader.getFullName()+"|"+reader.getAddress()+"|"+reader.getPhoneNumber());

        Reader reader = new Reader(Integer.parseInt(datas[0]), datas[1],datas[2], datas[3]);
        return reader;
    }
    public ArrayList<Book> readBooksFromFile(String fileName){
        openFiletoRead(fileName);
        ArrayList<Book> books = new ArrayList<>();
        while (scanner.hasNextLine()){
            String data = scanner.nextLine();
            Book book = createBookFromData(data);
            books.add(book);
        }
        closeFileAfterRead(fileName);
        return books;
    }

    public Book createBookFromData(String data) {
        String[] datas = data.split("\\|");
        //book.getBookID()+"|"
        //                +book.getBookName()+"|"+book.getAuthor()+"|"+book.getSpecialization()+"|"
        //                + book.getPublishYear()+"|"+book.getQuantity());
        Book book = new Book(Integer.parseInt(datas[0]), datas[1], datas[2], datas[3],
                Integer.parseInt(datas[4]),Integer.parseInt(datas[5]));
        return book;
    }
    public ArrayList<BookReaderManagement> readBRMsFromFile(String fileName){
        openFiletoRead(fileName);
        ArrayList<BookReaderManagement> brms = new ArrayList<>();
        while (scanner.hasNextLine()){
            String data = scanner.nextLine();
            BookReaderManagement reader = createBRMFromData(data);
            brms.add(reader);
        }
        closeFileAfterRead(fileName);
        return  brms;
    }


    public BookReaderManagement createBRMFromData(String data) {
        String[] datas = data.split("\\|");


        BookReaderManagement brm = new BookReaderManagement(new Book(Integer.parseInt(datas[1])),
                new Reader(Integer.parseInt(datas[0])),
                Integer.parseInt(datas[2]), datas[3], 0);
        return brm;
    }

}
