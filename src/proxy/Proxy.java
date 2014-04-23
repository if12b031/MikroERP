package proxy;

import invoice.Invoice;
import invoice.InvoiceList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import com.thoughtworks.xstream.XStream;

import contacts.Customer;
import contacts.CustomerList;

public class Proxy {

	public List<Customer> listAllCustomers() {
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
	
	public List<Customer> searchCustomer(String name, String lastName, String company) {
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
			
	        out.println("GET /MikroERP_Facade/searchContacts HTTP/1.1");
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
	
	public List<Invoice> searchInvoice(String dateFrom, String dateTo, String ValueFrom,
											String ValueTo, String customer) {
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
		}
		return null;
	}
}
