package proxy;

import invoice.Invoice;
import invoice.InvoiceElement;
import invoice.InvoiceList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;

import contacts.Customer;
import contacts.CustomerList;

public class Proxy {
	
	public ArrayList<Customer> searchCustomer(String name, String lastName, String company) {
		try {
			String XMLResponse = "";
			String buffer;
			StringBuilder strBuilder = new StringBuilder();
			XStream xs = new XStream();
			Socket socket = new Socket("localhost", 8080);
	        PrintWriter out = new PrintWriter(socket.getOutputStream());
	        BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                    		socket.getInputStream()));
			
	        out.println("GET /MikroERP_Facade/listAllContacts HTTP/1.1");
			out.flush();	        
	
	        while ((buffer = in.readLine()) != null){
	        	strBuilder.append(buffer);
	        }
	        
	        XMLResponse = strBuilder.toString();
	        
	        in.close();
	        socket.close();
	        
	        xs.alias("Customer", Customer.class);
			xs.alias("CustomerList", CustomerList.class);
			xs.addImplicitCollection(CustomerList.class, "_customerList");
			
	        CustomerList searchResult = (CustomerList) xs.fromXML(XMLResponse);
	        return searchResult.getCustomers();
			
		} catch (IOException e){
			System.out.println("Failed to create new socket!");
		} catch (NullPointerException e){
			System.out.println("No search results!");
		}
		
		/*CustomerList c = new CustomerList();
		Customer m = new Customer();
		m.set_title("Dr.");
		m.set_surname("Georg");
		m.set_lastname("Huszar");
		m.set_dateOfBirth("1992-01-18");
		m.set_address("Weizenweg 14");
		m.set_plz(1220);
		m.set_city("Wien");		
		c.add(m);
		c.add(m);
		c.add(m);
		c.add(m);
		c.add(m);
		c.add(m);
		c.add(m);
		return c.getCustomers();*/
		return null;
	}
	
	public ArrayList<Invoice> searchInvoice(String customer, String dateFrom, String dateTo, String valueFrom,
											String valueTo) {
		try {
			String XMLResponse = "";
			String buffer;
			StringBuilder strBuilder = new StringBuilder();
			XStream xs = new XStream();
			Socket socket = new Socket("localhost", 8080);
	        PrintWriter out = new PrintWriter(socket.getOutputStream());
	        BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                    		socket.getInputStream()));

	        out.println("GET /MikroERP_Facade/searchInvoices?name=" + customer + "&fromDate=" + dateFrom + "&toDate=" + dateTo
	        		+ "&fromAmount=" + valueFrom + "&toAmount=" + valueTo + " HTTP/1.1");
			out.flush();	        
	
	        while ((buffer = in.readLine()) != null){
	        	strBuilder.append(buffer);
	        }
	        
	        XMLResponse = strBuilder.toString();
	        
	        in.close();
	        socket.close();
	        
	        xs.alias("Invoice", Invoice.class);
			xs.alias("InvoiceList", InvoiceList.class);
			xs.addImplicitCollection(InvoiceList.class, "_invoiceList");
			
			InvoiceList searchResult = (InvoiceList) xs.fromXML(XMLResponse);
	        return searchResult.getInvoices();
			
		} catch (IOException e){
			System.out.println("Failed to create new socket!");
		} catch (NullPointerException e){
			System.out.println("No search results!");
		}
		
		/*InvoiceList i = new InvoiceList();
		Invoice c = new Invoice();
		InvoiceElement e = new InvoiceElement();
		ArrayList<InvoiceElement> a = new ArrayList<InvoiceElement>();
		e.set_name("Hose");
		e.set_amount(5);
		e.set_price(99.90);
		a.add(e);
		a.add(e);
		a.add(e);
		a.add(e);
		a.add(e);
		a.add(e);
		a.add(e);
		c.set_invoiceNumber(12);
		c.set_customerName("Georg Huszar");
		c.set_isOutgoing(true);
		c.set_creationDate("2001-01-05");
		c.set_net(100.00);
		c.set_ust(20.00);
		c.set_gross(120.00);
		c.set_articles(a);
		i.add(c);
		i.add(c);
		i.add(c);
		i.add(c);
		i.add(c);
		i.add(c);
		i.add(c);
		return i.getInvoices();*/
		return null;
	}
	
	public void createCustomer(Customer customer) {
	
	}
	
	public void createInvoice(Invoice invoice) {
		
	}
	
	public ArrayList<InvoiceElement> getArticles() {
		InvoiceElement ie = new InvoiceElement();
		InvoiceElement ie2 = new InvoiceElement();
		InvoiceElement ie3 = new InvoiceElement();
		ie.set_name("Hose");
		ie.set_price(100);
		ArrayList<InvoiceElement> al = new ArrayList<InvoiceElement>();
		al.add(ie);
		ie2.set_name("Shirt");
		ie2.set_price(25);
		al.add(ie2);
		ie3.set_name("Krawatte");
		ie3.set_price(19.90);
		al.add(ie3);
		return al;
	}
}
