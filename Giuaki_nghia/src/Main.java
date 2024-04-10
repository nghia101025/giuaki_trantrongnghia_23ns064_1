import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Main extends Thread {
    private List<Student> danhsach = new ArrayList<Student>();

    public static void main(String[] args) {
        Main mainThread = new Main();
        mainThread.start();
    }

    @Override
    public void run() {
        try {
            DocumentBuilderFactory docment = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = docment.newDocumentBuilder();
            Document doc = db.parse("Student.xml");
            Element element = doc.getDocumentElement();

            NodeList ID = element.getElementsByTagName("ID");
            NodeList name = element.getElementsByTagName("name");
            NodeList address = element.getElementsByTagName("address");
            NodeList age = element.getElementsByTagName("dateOfBirth");

            for (int i = 0; i < name.getLength(); i++) {
                Student student = new Student();
                student.setID(ID.item(i).getTextContent());
                student.setName(name.item(i).getTextContent());
                student.setAddress(address.item(i).getTextContent());
                String ageContent = age.item(i).getTextContent();
                LocalDate dateOfBirth = LocalDate.parse(ageContent);
                student.setDateOfBirth(dateOfBirth);
                danhsach.add(student);
            }

            for (Student student : danhsach) {
                MyThread thread = new MyThread(student);
                thread.MyThread2();
                thread.MyThread3();
                thread.writeToXML(danhsach, "kq.xml");
            }

        } catch (ParserConfigurationException | SAXException | IOException ex) {
            ex.printStackTrace();
        }
    }
}
