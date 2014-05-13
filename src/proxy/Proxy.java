package proxy;

import invoice.CalculatedValues;
import invoice.Invoice;
import invoice.InvoiceElement;
import invoice.InvoiceList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

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
			
	        out.println("GET /MikroERP_Facade/searchContacts?businessname=" + company + "&surname=" + name 
	        		+ "&lastname=" + lastName + " HTTP/1.1");
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
		return null;
	}
	
	public int createCustomer(Customer customer) {
		try {
			String response = "";
			String buffer;
			StringBuilder strBuilder = new StringBuilder();
			XStream xs = new XStream(new StaxDriver());
			Socket socket = new Socket("localhost", 8080);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(
	                		socket.getInputStream()));
	        
	        xs.alias("Customer", Customer.class);
	        // OBJECT --> XML
	     	String xml = xs.toXML(customer);
	        out.println("GET /MikroERP_Facade/createCustomer?customer=" + xml + " HTTP/1.1");
			out.flush();
			
			while ((buffer = in.readLine()) != null){
		       	strBuilder.append(buffer);
		    }			 
			response = strBuilder.toString();
			
			socket.close();
			if(response.equals("")){
				return 1;
			}
			return Integer.parseInt(response);
		} catch (UnknownHostException e) {
			System.out.println("Failed to create new socket! Unknown Host");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	public int createInvoice(Invoice invoice) {
		try {
			String response = "";
			String buffer;
			StringBuilder strBuilder = new StringBuilder();
			XStream xs = new XStream(new StaxDriver());
			Socket socket = new Socket("localhost", 8080);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(
	                		socket.getInputStream()));

	        xs.alias("Invoice", Invoice.class);
			xs.alias("InvoiceElement", InvoiceElement.class);
	        // OBJECT --> XML
	     	String xml = xs.toXML(invoice);
	        out.println("GET /MikroERP_Facade/createInvoice?invoice=" + xml + " HTTP/1.1");
			out.flush();
			
			while ((buffer = in.readLine()) != null){
		       	strBuilder.append(buffer);
		    }			 
			response = strBuilder.toString();
			
			socket.close();
			if(response.equals("")){
				return 1;
			}
			return Integer.parseInt(response);
		} catch (UnknownHostException e) {
			System.out.println("Failed to create new socket! Unknown Host");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	public ArrayList<InvoiceElement> getArticles() {
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

	        out.println("GET /MikroERP_Facade/getArticles HTTP/1.1");
			out.flush();	        
	
	        while ((buffer = in.readLine()) != null){
	        	strBuilder.append(buffer);
	        }
	        
	        XMLResponse = strBuilder.toString();
	        
	        in.close();
	        socket.close();
	        
	        xs.alias("InvoiceElement", InvoiceElement.class);			
			@SuppressWarnings("unchecked")
			ArrayList<InvoiceElement> searchResult = (ArrayList<InvoiceElement>) xs.fromXML(XMLResponse);
	        return searchResult;			
		} catch (IOException e){
			System.out.println("Failed to create new socket!");
		} catch (NullPointerException e){
			System.out.println("No search results!");
		}
		return null;
	}
	
	public CalculatedValues calculateValue(ArrayList<Double> prices) {
		try {
			String XMLResponse = "";
			String buffer;
			StringBuilder strBuilder = new StringBuilder();
			XStream xs = new XStream(new StaxDriver());
			Socket socket = new Socket("localhost", 8080);
	        PrintWriter out = new PrintWriter(socket.getOutputStream());
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(
	                		socket.getInputStream()));
	        
	        // OBJECT --> XML
	     	String xml = xs.toXML(prices);
	        out.println("GET /MikroERP_Facade/calculateValue?calculate=" + xml +" HTTP/1.1");
			out.flush();	        
	
	        while ((buffer = in.readLine()) != null){
	        	strBuilder.append(buffer);
	        }
	        
	        XMLResponse = strBuilder.toString();
	        
	        in.close();
	        socket.close();
	        
	        xs.alias("CalculatedValues", CalculatedValues.class);			
			CalculatedValues values = (CalculatedValues) xs.fromXML(XMLResponse);
	        return values;
		} catch (IOException e){
			System.out.println("Failed to create new socket!");
		} catch (NullPointerException e){
			System.out.println("No search results!");
		}
		return null;
	}
}
