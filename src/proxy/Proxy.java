package proxy;

import invoice.Invoice;
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
		return null;
	}
	
	public ArrayList<Invoice> searchInvoice(String dateFrom, String dateTo, String ValueFrom,
											String ValueTo, String customer) {
		/*try {
			String XMLResponse = "";
			String buffer;
			StringBuilder strBuilder = new StringBuilder();
			XStream xs = new XStream();
			Socket socket = new Socket("localhost", 8080);
	        PrintWriter out = new PrintWriter(socket.getOutputStream());
	        BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                    		socket.getInputStream()));
			
	        out.println("GET /MikroERP_Facade/searchInvoices HTTP/1.1");
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
		}*/
		
		InvoiceList i = new InvoiceList();
		Invoice c = new Invoice();
		c.set_invoiceNumber(12);
		c.set_customerName("Georg");
		c.set_customerLastname("Huszar");
		c.set_isOutgoing(true);
		c.set_creationDate("2001-01-05");
		c.set_net(100.00);
		c.set_ust(20.00);
		c.set_gross(120.00);
		i.add(c);
		i.add(c);
		i.add(c);
		i.add(c);
		i.add(c);
		i.add(c);
		i.add(c);
		return i.getInvoices();
	}
	
	public void createCustomer(Customer customer) {
	
	}
	
	public void createInvoice(Invoice invoice) {
		
	}
}
