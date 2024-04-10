import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class MyThread extends Thread {
    private Student student;

    private Period age;

    public MyThread(Student student) {
        this.student = student;
    }

    public void MyThread2() {
        LocalDate dob = student.getDateOfBirth();
        LocalDate now = LocalDate.now();
        age = Period.between(dob, now);
        System.out.println("?????????????");
        System.out.println("tuổi của:" + student.getName());
        System.out.print("Nam: " + age.getYears());
        System.out.print(" thang: " + age.getMonths());
        System.out.println(" ngay: " + age.getDays());

        String mahoa = mahoa(age.getYears(), age.getMonths(), age.getDays());
        System.out.println("Tuổi sau khi mã hóa "  + ": " + mahoa);

    }
    private int dateOfbirth;
    public void MyThread3() {
        LocalDate dob = student.getDateOfBirth();

        dateOfbirth = Sum(dob.toString().replace("-", ""));
        System.out.println("????????????");
        System.out.println("Tổng các ngày sinh :"+ dateOfbirth);
        if (isPrime(dateOfbirth)) {
            System.out.println(dateOfbirth + " là số nguyên tố");
        } else {
            System.out.println(dateOfbirth + " không phải là một số nguyên tố");

        }

    }
    private int Sum(String number) {
        int sum = 0;
        for (int i = 0; i < number.length(); i++) {
            sum += Character.getNumericValue(number.charAt(i));
        }
        return sum;
    }
    private String mahoa(int years, int months, int days) {
        return String.valueOf(years)+ String.valueOf(days)+String.valueOf(months)+ String.valueOf(days) + String.valueOf(days);
    }
    private boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    public void writeToXML(List<Student> students, String filename) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            // Tạo phần tử gốc <students>
            Element rootElement = doc.createElement("students");
            doc.appendChild(rootElement);

            for (Student student : students) {
                Element studentElement = doc.createElement("student");
                rootElement.appendChild(studentElement);

                Element yearElement = doc.createElement("Year");
                yearElement.appendChild(doc.createTextNode(String.valueOf(age.getYears())));
                studentElement.appendChild(yearElement);

                Element monthElement = doc.createElement("Months");
                monthElement.appendChild(doc.createTextNode(String.valueOf(age.getMonths())));
                studentElement.appendChild(monthElement);

                Element dayElement = doc.createElement("Days");
                dayElement.appendChild(doc.createTextNode(String.valueOf(age.getDays())));
                studentElement.appendChild(dayElement);

                MyThread thread3 = new MyThread(student);
                thread3.start();

                Element sumElement = doc.createElement("sum");
                sumElement.appendChild(doc.createTextNode(String.valueOf(dateOfbirth)));
                studentElement.appendChild(sumElement);

                Element isDigitElement = doc.createElement("isDigit");
                isDigitElement.appendChild(doc.createTextNode(String.valueOf(isPrime(dateOfbirth))));
                studentElement.appendChild(isDigitElement);
            }

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(filename);
            transformer.transform(source, result);

        } catch (ParserConfigurationException | TransformerException ex) {
            ex.printStackTrace();
        }
    }







}

