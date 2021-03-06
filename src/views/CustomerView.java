package views;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import constants.CONSTANTS;
import constants.Utility;
import controllers.CustomerController;
import dataAccessObjects.EmployeeDao;
import models.BaseService;
import models.Car;
import models.Customer;
import models.Employee;
import models.Fault;
import models.Invoice;
import models.Repair;
import oracle.jdbc.Const;

public class CustomerView {
	private static Scanner console = new Scanner(System.in);
	private static CustomerController controller;
	
	public static CustomerController getController() {
		return controller;
	}

	public void setController(CustomerController controller) {
		CustomerView.controller = controller;
	}

	public String displayMainMenu() {
		
		int choice=0;
		System.out.println("--------MENU-------");
		System.out.println("1. Profile");
		System.out.println("2. Register Car");
		System.out.println("3. Service");
		System.out.println("4. Invoices");
		System.out.println("5. Logout");
		
		while(true) {
			try {
				
				
				System.out.print(">");
				
				choice = Integer.parseInt(console.nextLine());
				if( choice >5 || choice <1) {
					
					System.out.println("< Error: Choice not correct, Try again ");
					
				}
				else {
					break;
				}
		     
			}catch(Exception e) {
				
				 System.out.println("< Error: Choice not correct, Try again ");
				 console.reset();
			}
	            
	        }
		if (choice==5) {
			return CONSTANTS.LOGOUT;
		}
		
	
		return CONSTANTS.CUSTOMER_MAIN_MENU+choice;
	}

	public String displayProfileMenu() {
		
		int choice=0;
		System.out.println("--------MENU-------");
		System.out.println("1. View Profile ");
		System.out.println("2. Update Profile ");
		System.out.println("3. Go Back ");
		
		while(true) {
			try {
				
				
				System.out.print(">");
				
				choice = Integer.parseInt(console.nextLine());
				if( choice >3|| choice <1) {
					
					System.out.println("< Error: Choice not correct, Try again ");
					
				}
				else {
					break;
				}
		     
			}catch(Exception e) {
				
				 System.out.println("< Error: Choice not correct, Try again ");
				 console.reset();
			}
	            
	        }
		
		if (choice==3) {
			return CONSTANTS.CUSTOMER_MAIN_MENU;
		}
		
		
		return CONSTANTS.CUSTOMER_PROFILE+choice;
		
	}

	public String viewProfile(Customer customer) {
		
		int choice=0;
		
		System.out.println("--------Profile-------- ");
		System.out.println("Customer ID:  "+customer.getcId());
		System.out.println("Name:  "+customer.getcName());
		System.out.println("Address:  "+customer.getAddress());
		System.out.println("Email Address:  "+customer.getEmail());
		System.out.println("Phone Number:  "+customer.getPhone());
		System.out.println("Cars Owned by Customer:  ");
		if (customer.getCarsOwned().size()==0) {
			System.out.println("Customer doesn't own any car");
		}
		for (int i=0;i<customer.getCarsOwned().size();i++) {
			Car car=customer.getCarsOwned().get(i);
			
			System.out.println("Car: "+car.getLicensePlate()+" ,Make: "+car.getMake()+",Model: "+car.getModel()+" ,Year: "+car.getMakeYear()+" ,Date of last Service: "+car.getDateOfService()+" ,Last Service Type: "+car.getLastServiceType()+" ,Date of Purchase: "+car.getDateOfPurchase()+" Last recorded Mileage: "+car.getLastMileage());

		}
		System.out.println("\n");
		System.out.println("--------MENU-------");
		System.out.println("1. Go Back");
		while(true) {
			try {
				System.out.print(">");
				
				choice = Integer.parseInt(console.nextLine());
				if( choice!=1) {
					
					System.out.println("< Error: Choice not correct, Try again ");
					
				}
				else {
					break;
				}
		     
			}catch(Exception e) {
				
				 System.out.println("< Error: Choice not correct, Try again ");
				 console.reset();
			}
	            
	        }
		
		
		return CONSTANTS.CUSTOMER_PROFILE;
	}

	public String updateProfile(Customer customer) {
		int choice=0;
		
		boolean flag=true;
		while(flag) {
			try {
				System.out.println("--------MENU-------");
				System.out.println("1. Name");
				System.out.println("2. Address ");
				System.out.println("3. Phone Number ");
				System.out.println("4. Password ");
				System.out.println("5. Go Back ");
				
				System.out.print("> Choose 1-5 to enter new value: ");
				System.out.println(">");
				try {
				choice = Integer.parseInt(console.nextLine());
				}
				catch(NumberFormatException e) {
					System.out.println("< Error: Enter Number only ");
				}
				if( choice >5|| choice <1) {
					
					System.out.println("< Error: Choice not correct, Try again ");
					
				}
				else {
					String input="";
					switch(choice) {
					case 1: 
						System.out.println("> Enter new Name:  ");
						input="";
						while(input.equals("")) {
							input=(console.nextLine()).trim();
							if (input.equals("")){
								System.out.println("Error: Name is blank. ");
							}
						}
						
						customer.setcName(input);
						break;
					case 2: 
						System.out.println("> Enter new Address:  ");
						input="";
						while(input.equals("")) {
							input=(console.nextLine()).trim();
							if (input.equals("")){
								System.out.println("Error: Address is blank. ");
							}
						}
						customer.setAddress(input);
						break;	
					case 3: 
						System.out.println("> Enter new Phone Number:  ");
						input="";
						while(input.equals("")) {
							input=(console.nextLine()).trim();
							
							if (input.equals("")){
								System.out.println("Error: Phone Number is blank. ");
							}else if(!Utility.isValidPhoneNumber(input)) {
								System.out.println("Error: Phone Number not correct ");
								input="";
							}
						}
						input=input.replaceAll("-", "");
						customer.setPhone(Long.parseLong(input.replaceAll("\\.", "")));
						break;	
						
					case 4: 
						System.out.println("> Enter new Password:  ");
						input="";
						while(input.equals("")) {
						
							input=(console.nextLine()).trim();
							
							if (input.equals("")){
								System.out.println("Error: Password is blank. ");
							}
							
				
						}
						customer.setPassword(input);
						break;	
					case 5: 
						flag=false;
						break;	
					}
					
				}
		     	
				
		     
			}catch(Exception e) {
				
				 System.out.println("< Error: Choice not correct, Try again ");
				 console.reset();
			}
	            
	        }
		
		
		return CONSTANTS.CUSTOMER_PROFILE;
		
	}

	public String registerCar(Customer customer) {
		int choice=0;
		String input="";
		Car car=new Car();
		car.setcId(customer.getcId());
		System.out.println("--------Register Car-------");
		
		System.out.println("Enter Cancel to go back");
		System.out.println("A. Enter Licence Plate ");
		while(input.equals("")) {
			
			
			System.out.print(">");
			input=(console.nextLine()).trim();
			if (input.equalsIgnoreCase("cancel")) {
				return CONSTANTS.CUSTOMER_MAIN_MENU;
			}
			if (input.equals("")){
				System.out.println("< Error: Licence plate is mandatory ");
			}
		}
		car.setLicensePlate(input);
		input="";
		System.out.println("B. Enter Purchase date in format-MM/dd/yyyy ");
		Date date1=null;
		while(input.equals("")) {
			
			System.out.print(">");
			input=(console.nextLine()).trim();
			if (input.equalsIgnoreCase("cancel")) {
				return CONSTANTS.CUSTOMER_MAIN_MENU;
			}
			if (input.equals("")){
				System.out.println("< Error: Purchase date is mandatory ");
			}else if (!Utility.isDateValid(input)) {
				System.out.println("< Error: Date not in correct format ");
				input="";	
			}else {
				try {
					date1=new SimpleDateFormat("MM/dd/yyyy").parse(input);  
				   
					}
					catch(ParseException e) {
						System.out.println("< Error: Date not in correct format ");
						input="";	
					}
			}
			
		
		}
		
		car.setDateOfPurchase(date1);
		
		input="";
		System.out.println("C. Enter Make of the car(TOYOTA,NISSAN,HONDA) ");
		while(input.equals("")) {
			
			System.out.print(">");
			input=(console.nextLine()).trim();
			if (input.equalsIgnoreCase("cancel")) {
				return CONSTANTS.CUSTOMER_MAIN_MENU;
			}
			if (input.equals("")){
				System.out.println("< Error: Make is mandatory ");
			}else if(!input.equalsIgnoreCase("TOYOTA") && !input.equalsIgnoreCase("NISSAN") && !input.equalsIgnoreCase("HONDA")) {
				System.out.println("< Error: Make is not correct ");
				input="";
			}
			
		}
		
		car.setMake(input.toUpperCase());
		if (car.getMake().equals("TOYOTA")) {
			input="";
			System.out.println("D. Enter Model of the car(Corolla,Prius) ");
			while(input.equals("")) {
			
				System.out.print(">");
				input=(console.nextLine()).trim();
				if (input.equalsIgnoreCase("cancel")) {
					return CONSTANTS.CUSTOMER_MAIN_MENU;
				}
				if (input.equals("")){
					System.out.println("< Error: Model is mandatory ");
				}else if(!input.equalsIgnoreCase("Corolla") && !input.equalsIgnoreCase("Prius")) {
					System.out.println("< Error: Model is not correct ");
					input="";
				}
				
			}
			
			car.setModel(input.toUpperCase());
			
		}else if (car.getMake().equals("NISSAN")) {
			input="";
			System.out.println("D. Enter Model of the car(Altima,Rogue) ");
			while(input.equals("")) {
			
				System.out.print(">");
				input=(console.nextLine()).trim();
				if (input.equalsIgnoreCase("cancel")) {
					return CONSTANTS.CUSTOMER_MAIN_MENU;
				}
				if (input.equals("")){
					System.out.println("< Error: Model is mandatory ");
				}else if(!input.equalsIgnoreCase("Altima") && !input.equalsIgnoreCase("Rogue")) {
					System.out.println("< Error: Model is not correct ");
					input="";
				}
				
			}
			
			car.setModel(input.toUpperCase());
			
		}else if (car.getMake().equals("HONDA")) {
			input="";
			System.out.println("D. Enter Model of the car(Accord,Civic) ");
			while(input.equals("")) {
			
				System.out.print(">");
				input=(console.nextLine()).trim();
				if (input.equalsIgnoreCase("cancel")) {
					return CONSTANTS.CUSTOMER_MAIN_MENU;
				}
				if (input.equals("")){
					System.out.println("< Error: Model is mandatory ");
				}else if(!input.equalsIgnoreCase("Accord") && !input.equalsIgnoreCase("Civic")) {
					System.out.println("< Error: Model is not correct ");
					input="";
				}
				
			}
			
			car.setModel(input.toUpperCase());
			
		}
		
		
		input="";
		int year=0;
		System.out.println("E. Enter Car Model year ");
		while(input.equals("")) {
	
			System.out.print(">");
			input=(console.nextLine()).trim();
			if (input.equalsIgnoreCase("cancel")) {
				return CONSTANTS.CUSTOMER_MAIN_MENU;
			}
			if (input.equals("")){
				System.out.println("< Error: Year is mandatory ");
			}
			try {
			 year=Integer.parseInt(input);
			
			if (input.length()!=4 || year<1920 || year>Calendar.getInstance().get(Calendar.YEAR) ) {
				System.out.println("< Error: Year is not correct ");
				input="";
				
			}
			}catch(NumberFormatException e) {
				System.out.println("< Error: Year is not correct, should be a number. ");
				input="";
			}
			
			
		}
		
		car.setMakeYear(year);
		

		System.out.println("F. Current mileage ");
		input="";
		int mileage=0;
		while(input.equals("")) {
			
			System.out.print(">");
			input=(console.nextLine()).trim();
			if (input.equalsIgnoreCase("cancel")) {
				return CONSTANTS.CUSTOMER_MAIN_MENU;
			}
			if (input.equals("")){
				System.out.println("< Error: mileage is mandatory ");
			}
			try {
				mileage=Integer.parseInt(input);
			}catch(NumberFormatException e) {
				System.out.println("< Error: mileage is not correct, should be a number. ");
				input="";
			}
			
			
		}
		
		car.setLastMileage(mileage);
		
		
		
		input="";
		boolean flag=false;
		
			System.out.println("G. Last Service Date in MM/dd/yyyy format");
			System.out.print(">");
			do {
			input=(console.nextLine()).trim();
			if (input.equalsIgnoreCase("cancel")) {
				return CONSTANTS.CUSTOMER_MAIN_MENU;
			}
			if (!input.equals(""))  {
				 if (!Utility.isDateValid(input)) {
					System.out.println("< Error: Date not in correct format ");
					input="";
					flag=true;
				}else {
					try {
						date1=new SimpleDateFormat("MM/dd/yyyy").parse(input);  
					   flag=false;
						}
						catch(ParseException e) {
							System.out.println("< Error: Date not in correct format ");
							input="";	
							flag=true;
						}
				}
			}
			}while(flag);
				

			
		
		
		car.setDateOfService(input.equals("")?null:date1);
		
		
		
		
		System.out.println("--------MENU-------");
		System.out.println("1. Register ");
		System.out.println("2. Cancel ");
		
		while(true){
			try {
				
				
				System.out.print(">");
				
				choice = Integer.parseInt(console.nextLine());
				if( choice >2|| choice <1) {
					
					System.out.println("< Error: Choice not correct, Try again ");
					
				}
				else {
					break;
				}
		     
			}catch(Exception e) {
				
				 System.out.println("< Error: Choice not correct, Try again ");
				 console.reset();
			}
	            
	        
		}
		
		
		if (choice==1) {
			if (controller.registerCar(car))
				{
				System.out.println("Car Registered");}
				else {
					System.out.println("Something Went wrong, try again ");
					 return CONSTANTS.CUSTOMER_REGISTER_CAR;
				}
			
		}else {
			System.out.println("Registeration canceled");
		}
		
		
		return CONSTANTS.CUSTOMER_MAIN_MENU;
		
	}

	public String viewServiceMenu() {
		int choice=0;
		System.out.println("--------MENU-------");
		System.out.println("1. View Service History ");
		System.out.println("2. Schedule Service ");
		System.out.println("3. Reschedule Service ");
		System.out.println("4. Go Back ");
		while(true) {
			try {
				
				
				System.out.print(">Enter Choice:");
				
				choice = Integer.parseInt(console.nextLine());
				if( choice >4|| choice <1) {
					
					System.out.println("< Error: Choice not correct, Try again ");
					
				}
				else {
					break;
				}
		     
			}catch(Exception e) {
				
				 System.out.println("< Error: Choice not correct, Try again ");
				 console.reset();
			}
	            
	        }
		
		if (choice==4) {
			return CONSTANTS.CUSTOMER_MAIN_MENU;
		}
		return CONSTANTS.CUSTOMER_SERVICE+choice;
		
	}

	public String viewServiceHistory(ArrayList<Repair> repairs) {
		int choice=0;
		
		System.out.println("--------Service History-------- ");
		
		
		if (repairs.size()==0) {
			System.out.println("No Service found");
		}
		for (int i=0;i<repairs.size();i++) {
			Repair repair=repairs.get(i);
			
			System.out.println("Service ID: "+repair.getInvoiceNumber()+" License Plate: "+repair.getCar().getLicensePlate()+" Service Type: "+repair.getServiceType()+" Mechanic Name: "+
			repair.getMechanicName()+" Service Start Date/Time: "+repair.getRdate()+" "+repair.getStartTime()+" Service End Date/Time: "+repair.getRdate()+" "+ repair.getEndTime	()+
			" Service Status: "+repair.getStatus());
			
		}
		System.out.println("\n");
		System.out.println("--------MENU-------");
		System.out.println("1. Go Back");
		while(true) {
			try {
				System.out.print(">");
				
				choice = Integer.parseInt(console.nextLine());
				if( choice!=1) {
					
					System.out.println("< Error: Choice not correct, Try again ");
					
				}
				else {
					break;
				}
		     
			}catch(Exception e) {
				
				 System.out.println("< Error: Choice not correct, Try again ");
				 console.reset();
			}
	            
	        }
		
		
		return CONSTANTS.CUSTOMER_SERVICE;
	}

	public String viewServiceSchedule(Customer customer, int centerID) {
		int choice=0;
		String input="";
		Repair service=new Repair();
		service.setCenterId(centerID);
		Car car=new Car();
		service.setcId(customer.getcId());
		System.out.println("--------Schedule the service -------");
		System.out.println("Enter Cancel to go back");
		System.out.println("A. Enter Licence Plate ");
		while(input.equals("")) {
			
			
			System.out.print(">");
			input=(console.nextLine()).trim();
			if (input.equalsIgnoreCase("cancel")) {
				return CONSTANTS.CUSTOMER_SERVICE;
			}
			if (input.equals("")){
				System.out.println("< Error: Licence plate is mandatory ");
			}
		}
		car.setLicensePlate(input);
		car.setcId(customer.getcId());
		input="";
		int mileage=0;

		System.out.println("B. Enter current mileage of the car ");
		while(input.equals("")) {
			System.out.print(">");
			input=(console.nextLine()).trim();
			if (input.equalsIgnoreCase("cancel")) {
				return CONSTANTS.CUSTOMER_SERVICE;
			}
			if (input.equals("")){
				System.out.println("< Error: current mileage is mandatory ");
			}
			try {
				mileage=Integer.parseInt(input);
			}catch(NumberFormatException e) {
				System.out.println("< Error: mileage is not correct, should be a number. ");
				input="";
			}
			
			
		}
		
		car.setNewMileage(mileage);
		service.setCar(car);
		input="";
		EmployeeDao empDao=new EmployeeDao();
		ArrayList<Employee> mechanics=empDao.getAllMechanic(service.getCenterId());
		System.out.println("Enter Cancel to go back");
		System.out.println("C. Select Mechanic (Enter 1-"+mechanics.size()+"): ");
		for (int i =0;i<mechanics.size();i++) {
			
			System.out.println((i+1) +". "+mechanics.get(i).geteName());
		}
		int mechanic=0;
		while(true){
			try {
				
				
				System.out.print(">");
				input=console.nextLine().trim();
				if (input.equalsIgnoreCase("cancel")) {
					return CONSTANTS.CUSTOMER_SERVICE;
				}
				if (input.equals("")){
					break;
				}
				mechanic = Integer.parseInt(input);
				if( mechanic >mechanics.size()|| mechanic <1) {
					
					System.out.println("< Error: Mechanic selected not correct, Try again ");
					
				}
				else {
					break;
				}
		     
			}catch(Exception e) {
				
				 System.out.println("< Error: Mechanic selected not correct, Enter the number. ");
				 console.reset();
			}
			
		}
		if( !input.equals("")){
			service.setMechanicName(mechanics.get(mechanic-1).geteName());
			service.setMechanicId(mechanics.get(mechanic-1).geteId());
		}
		
		customer.setService(service);
		return CONSTANTS.CUSTOMER_SERVICE_SCHEDULE2;
	}
		
	public String viewSchedule(Customer customer) {
		
		Repair service=customer.getService();
		System.out.println("--------MENU-------");
		System.out.println("1. Schedule Maintenance ");
		System.out.println("2. Schedule Repair ");
		System.out.println("3. Go Back ");
		int choice=0;
		while(true){
			try {
				
				
				System.out.print(">");
				
				choice = Integer.parseInt(console.nextLine());
				if( choice >3|| choice <1) {
					
					System.out.println("< Error: Choice not correct, Try again ");
					
				}
				else {
					break;
				}
		     
			}catch(Exception e) {
				
				 System.out.println("< Error: Choice not correct, Try again ");
				 console.reset();
			}
	            
	        
		}
		
		
		if (choice==1) {
			viewScheduleMaintenance(service,customer);
			
		}else if(choice==2) {
			
			ArrayList<Fault> faults=controller.getAllFaults(service);
			viewScheduleRepair(service,faults,customer);
			
		}
		
		
		return CONSTANTS.CUSTOMER_SERVICE;
	}

	private String viewScheduleRepair(Repair service, ArrayList<Fault> faults, Customer customer) {
		
		System.out.println("--------Schedule Repair (Page 1)-------");
		for (int i=0;i<faults.size();i++) {
			System.out.println((i+1)+". "+faults.get(i).getfName() );
		}
		
		System.out.println((faults.size()+1)+". Go Back ");
		int choice=0;
		
		while(true){
			try {
				
				
				System.out.print(">");
				
				choice = Integer.parseInt(console.nextLine());
				if( choice >faults.size()+1|| choice <1) {
					
					System.out.println("< Error: Choice not correct, Try again ");
					
				}
				else {
					break;
				}
		     
			}catch(Exception e) {
				
				 System.out.println("< Error: Choice not correct, Try again ");
				 console.reset();
			}
	            
	        
		}
		
		
		if (choice==faults.size()+1) {
			return CONSTANTS.CUSTOMER_SERVICE_SCHEDULE2;
		}else {
			
			if (controller.validateCar(service)) {
				Fault fault =faults.get(choice-1);
				service.setFault(fault);
				controller.getFaultDetails(fault,service.getCar().getCarTypeID(),service.getcId(),service.getCar().getMake());
				ArrayList<Employee> mechanics=controller.findDates(service,new Date());
				if (mechanics==null) {
					return CONSTANTS.CUSTOMER_SERVICE_SCHEDULE2;
				}
				
				viewScheduleRepair2(service,mechanics,customer,fault);
			}else {
				System.out.println("Car not valid Enter again");
				viewServiceSchedule(customer,customer.getCenterId()) ;
			}
			
			return CONSTANTS.CUSTOMER_SERVICE_SCHEDULE2;
			
		}
		
	}

	private void viewScheduleRepair2(Repair service, ArrayList<Employee> mechanics, Customer customer, Fault fault) {
		// TODO Auto-generated method stub
		System.out.println("--------Schedule Repair (Page 2) -------");
		System.out.println("-----------------Report-----------------");
		System.out.println("Fault: "+fault.getfName());
		System.out.println("Diagnostic: "+fault.getDiagnostic());
		System.out.println("Diagnostic fee: $"+fault.getFee());
		System.out.println("Service: ");
		for (int i=0;i<fault.getBs().size();i++) {
			BaseService bs=fault.getBs().get(i);
			System.out.println("	"+bs.getName()+"");
			System.out.println("		Parts:");
			for (int j=0;j<bs.getParts().size();j++) {
			System.out.println("			"+bs.getParts().get(j).getpName());
			}
		}
		System.out.println("--------------Schedule Dates-------------");
		for (int i=0;i<mechanics.size();i++) {
			System.out.println((i+1)+" Mechanic: "+mechanics.get(i).geteName()+" Available Date: "+mechanics.get(i).getStartTime()+" - "+mechanics.get(i).getEndTime());
		}
		System.out.println("----------------Menu---------------------");
		System.out.println("1. Repair on Date ");
		System.out.println("2. Go Back");
		int choice=0;
		
		while(true){
			try {
				
				
				System.out.print(">");
				
				choice = Integer.parseInt(console.nextLine());
				if( choice >2|| choice <1) {
					
					System.out.println("< Error: Choice not correct, Try again ");
					
				}
				else {
					break;
				}
		     
			}catch(Exception e) {
				
				 System.out.println("< Error: Choice not correct, Try again ");
				 console.reset();
			}
	            
	        
		}
		
		
		if (choice==1) {
			int dateSelected=0;
			
			while(true){
				try {
					
					
					System.out.print(">Choose the date(1/2)");
					
					dateSelected = Integer.parseInt(console.nextLine());
					if( dateSelected >2|| dateSelected <1) {
						
						System.out.println("< Error: Choice not correct, Try again ");
						
					}
					else {
						break;
					}
			     
				}catch(Exception e) {
					
					 System.out.println("< Error: Choice not correct, Try again ");
					 console.reset();
				}
		            
		        
			}
			
			controller.saveRepair(service,mechanics.get(dateSelected-1),fault);
			System.out.println("Repair Scheduled");
			
		}else if(choice==2) {
			ArrayList<Fault> faults=controller.getAllFaults(service);
			viewScheduleRepair(service, faults, customer);
			
			
		}
	}

	private String viewScheduleMaintenance(Repair service, Customer customer) {
		System.out.println("--------Schedule Maintenance (Page 1) -------");
		System.out.println("1. Find Service Date ");
		System.out.println("2. Go Back");
		int choice=0;
		
		while(true){
			try {
				
				
				System.out.print(">");
				
				choice = Integer.parseInt(console.nextLine());
				if( choice >2|| choice <1) {
					
					System.out.println("< Error: Choice not correct, Try again ");
					
				}
				else {
					break;
				}
		     
			}catch(Exception e) {
				
				 System.out.println("< Error: Choice not correct, Try again ");
				 console.reset();
			}
	            
	        
		}
		
		
		if (choice==1) {
			if (controller.validateCar(service)) {
				ArrayList<Employee> mechanics=controller.findDates(service,new Date());
				if (mechanics==null) {
					viewServiceSchedule(customer,customer.getCenterId());
				}
				viewScheduleMaintenance2(service,mechanics,customer);
			}else {
				System.out.println("Car not valid Enter again");
				viewServiceSchedule(customer,customer.getCenterId()) ;
			}
			
			
		}
		return CONSTANTS.CUSTOMER_SERVICE_SCHEDULE2;
			
			
		
	}

	private String viewScheduleMaintenance2(Repair service, ArrayList<Employee> mechanics, Customer customer) {
		// TODO Auto-generated method stub
		System.out.println("--------Schedule Maintenance Page2-------");
		for (int i=0;i<mechanics.size();i++) {
			System.out.println((i+1)+" Mechanic: "+mechanics.get(i).geteName()+" Available Date: " +mechanics.get(i).getStartTime()+" - "+mechanics.get(i).getEndTime());
		}
		
		System.out.println("-------Menu------");
		System.out.println("1. Schedule on Date ");
		System.out.println("2. Go Back");
		int choice=0;
		
		while(true){
			try {
				
				
				System.out.print(">");
				
				choice = Integer.parseInt(console.nextLine());
				if( choice >2|| choice <1) {
					
					System.out.println("< Error: Choice not correct, Try again ");
					
				}
				else {
					break;
				}
		     
			}catch(Exception e) {
				
				 System.out.println("< Error: Choice not correct, Try again ");
				 console.reset();
			}
	            
	        
		}
		
		
		if (choice==1) {
			int dateSelected=0;
			
			while(true){
				try {
					
					
					System.out.print(">Choose the date(1/2)");
					
					dateSelected = Integer.parseInt(console.nextLine());
					if( dateSelected >2|| dateSelected <1) {
						
						System.out.println("< Error: Choice not correct, Try again ");
						
					}
					else {
						break;
					}
			     
				}catch(Exception e) {
					
					 System.out.println("< Error: Choice not correct, Try again ");
					 console.reset();
				}
		            
		        
			}
			
			controller.saveMaintenance(service,mechanics.get(dateSelected-1));
			System.out.println("Service scheduled \n\n");
			viewServiceSchedule(customer,customer.getCenterId());
			
		}
		return viewScheduleMaintenance(service,customer);
			
			
		
	}

	public String viewServiceReSchedule(ArrayList<Repair> services, Customer customer) {
		int choice=0;
		
		System.out.println("--------Service History-------- ");
		
		
		if (services.size()==0) {
			System.out.println("No Service found");
		}
		for (int i=0;i<services.size();i++) {
			Repair repair=services.get(i);
			
			System.out.println((i+1)+". Service ID: "+repair.getInvoiceNumber()+" License Plate: "+repair.getCar().getLicensePlate()+" Service Type: "+repair.getServiceType()+" Mechanic Name: "+
			repair.getMechanicName()+" Service Start Date/Time: "+repair.getRdate()+" "+repair.getStartTime()+" Service End Date/Time: "+repair.getRdate()+" "+ repair.getEndTime	()+
			" Service Detail: "+repair.getServiceTypeDetail());
			
		}
		System.out.println("\n");
		System.out.println("--------MENU-------");
		System.out.println("1. Pick a service");
		System.out.println("2. Go Back");
		while(true) {
			try {
				System.out.print(">");
				
				choice = Integer.parseInt(console.nextLine());
				if( choice<1 || choice>2) {
					
					System.out.println("< Error: Choice not correct, Try again ");
					
				}
				else {
					break;
				}
		     
			}catch(Exception e) {
				
				 System.out.println("< Error: Choice not correct, Try again ");
				 console.reset();
			}
	            
	        }
		if (choice==1) {
			int serviceSelected=0;
			
			while(true){
				try {
					
					
					System.out.println("Enter one of the service from the list to reschedule (1-"+services.size()+") :");
					
					serviceSelected = Integer.parseInt(console.nextLine());
					if( serviceSelected >services.size()|| serviceSelected <1) {
						
						System.out.println("< Error: Choice not correct, Try again ");
						
					}
					else {
						break;
					}
			     
				}catch(Exception e) {
					
					 System.out.println("< Error: Choice not correct, Try again ");
					 console.reset();
				}
		            
		        
			}
			Repair selectedRepair=services.get(serviceSelected-1);
			ArrayList<Employee> mechanics=controller.findDatesReschedule(selectedRepair,selectedRepair.getRdate());
			viewServiceReSchedule2(services,selectedRepair,mechanics,customer);
			
		}
		
		
		
		return CONSTANTS.CUSTOMER_SERVICE;
	}

	private void viewServiceReSchedule2(ArrayList<Repair> services, Repair selectedRepair, ArrayList<Employee> mechanics, Customer customer) {
		System.out.println("--------Reschedule Service Page2-------");
		for (int i=0;i<mechanics.size();i++) {
			System.out.println((i+1)+" Mechanic: "+mechanics.get(i).geteName()+" Available Date: " +mechanics.get(i).getStartTime()+" - "+mechanics.get(i).getEndTime());
		}
		
		System.out.println("-------Menu------");
		System.out.println("1. Reschedule Date ");
		System.out.println("2. Go Back");
		int choice=0;
		
		while(true){
			try {
				
				
				System.out.print(">");
				
				choice = Integer.parseInt(console.nextLine());
				if( choice >2|| choice <1) {
					
					System.out.println("< Error: Choice not correct, Try again ");
					
				}
				else {
					break;
				}
		     
			}catch(Exception e) {
				
				 System.out.println("< Error: Choice not correct, Try again ");
				 console.reset();
			}
	            
	        
		}
		
		
		if (choice==1) {
			int dateSelected=0;
			
			while(true){
				try {
					
					
					System.out.print(">Choose the date(1/2)");
					
					dateSelected = Integer.parseInt(console.nextLine());
					if( dateSelected >2|| dateSelected <1) {
						
						System.out.println("< Error: Choice not correct, Try again ");
						
					}
					else {
						break;
					}
			     
				}catch(Exception e) {
					
					 System.out.println("< Error: Choice not correct, Try again ");
					 console.reset();
				}
		            
		        
			}
			
			controller.rescheduleService(selectedRepair,mechanics.get(dateSelected-1));
			System.out.println("Service rescheduled \n\n");
			viewServiceSchedule(customer,customer.getCenterId());
			
		}else if(choice==2) {
			viewServiceReSchedule(services,customer);
		
		}
		
	}

	public String viewServiceInvoice(HashMap<String, Repair> completedservices, Customer customer) {
		int choice=0;
		
		System.out.println("--------Invoice-------- ");
		
		
		if (completedservices.keySet().size()==0) {
			System.out.println("No Service found");
		}
		int i=1;
		  for (Map.Entry<String, Repair> me : completedservices.entrySet()) {
			Repair repair=(Repair) me.getValue();
			
			System.out.println((i)+". Service ID: "+repair.getInvoiceNumber()+" License Plate: "+repair.getCar().getLicensePlate()+" Service Type: "+repair.getServiceType()+" Mechanic Name: "+
			repair.getMechanicName()+" Service Start Date/Time: "+repair.getRdate()+" "+repair.getStartTime()+" Service End Date/Time: "+repair.getRdate()+" "+ repair.getEndTime	()+
			" Service Detail: "+repair.getServiceTypeDetail() +" Total Service Cost: "+ repair.getFees());
			i+=1;
		}
		System.out.println("\n");
		System.out.println("--------MENU-------");
		System.out.println("1. View Invoice Details");
		System.out.println("2. Go Back");
		while(true) {
			try {
				System.out.print(">");
				
				choice = Integer.parseInt(console.nextLine());
				if( choice<1 || choice>2) {
					
					System.out.println("< Error: Choice not correct, Try again ");
					
				}
				else {
					break;
				}
		     
			}catch(Exception e) {
				
				 System.out.println("< Error: Choice not correct, Try again ");
				 console.reset();
			}
	            
	        }
		if (choice==1) {
			
			viewInvoiceDetail(completedservices,customer);
			
		}
		
		
		
		return CONSTANTS.CUSTOMER_SERVICE;
		  }
	

	private void viewInvoiceDetail(HashMap<String, Repair> completedservices, Customer customer) {
		// TODO Auto-generated method stub
		int choice=0;
		Repair service;
		System.out.println("--------Invoice Details-------- ");

		System.out.println("Enter the Service ID");
		while(true) {
			
				System.out.println(">");
				String id = console.nextLine();
				System.out.println("Enter Cancel to go back");
				if (id.equalsIgnoreCase("cancel")) {
					return;
				}
			    service=completedservices.get(id);
			    if( service==null) {
					
					System.out.println("< Error: Service ID  not correct, Try again  ");
					
				}
				else {
					break;
				}
			
	            
	        }
		
		controller.getServiceDetail(service);
		
		System.out.println(" Service ID: "+service.getInvoiceNumber());

		System.out.println(" Service Start Date/Time: "+service.getRdate()+ " "+ service.getStartTime());
		System.out.println(" Service End Date/Time: "+service.getRdate()+" "+ service.getEndTime	());
		System.out.println(" License Plate: "+service.getCar().getLicensePlate());
		System.out.println(" Service Type: "+service.getServiceType());
		System.out.println(" Mechanic Name: "+ service.getMechanicName());
		System.out.println(" Services: ");
		int labourwages=0;
		int count=0;
		for (int i=0;i<service.getBaseServices().size();i++) {
			BaseService bs=service.getBaseServices().get(i);
			System.out.println("	"+bs.getName()+"");
			boolean lb=false;
			boolean war=false;
			for (int j=0;j<bs.getInvoice().size();j++) {
				Invoice inv=bs.getInvoice().get(j);
				String warr="";
				if (inv.isWarranty()) {
					war=true;
					warr=" Part is with the Warranty"; 		
				}
			System.out.println("		Part Name:"+inv.getPartName()+" Cost:"+inv.getCost()+warr);
			

			if (inv.isFirst()) {
				lb=true;
			}
			
			
			}
		
			
			if (lb==true) {
				
				System.out.println("		Service was first time provided so No labour charge"); 		
			}
			else {
				if (!war) {
					count+=1;
					labourwages+=bs.getLabourCharge();
					System.out.println("		Labour Charge per hour:"+bs.getLabourCharge()); 
					System.out.println("		Labour hours:"+bs.getHour()); 
				}
			}
			
			
		}
		labourwages=service.getBaseServices().size()==0?0: labourwages/count;
		float hours=(float)(service.getEndTime().getTime()-service.getStartTime().getTime())/3600000;
		System.out.println(" Total labour hours : "+hours );
		System.out.println(" Labour Wages per hour : "+labourwages );
		
		System.out.println(" Total Service Cost: "+ service.getFees());
		
	
		System.out.println("--------MENU-------");
		System.out.println("1. Go Back");
		while(true) {
			try {
				System.out.print(">");
				
				choice = Integer.parseInt(console.nextLine());
				if( choice!=1) {
					
					System.out.println("< Error: Choice not correct, Try again ");
					
				}
				else {
					break;
				}
		     
			}catch(Exception e) {
				
				 System.out.println("< Error: Choice not correct, Try again ");
				 console.reset();
			}
	            
	        }
		viewServiceInvoice(completedservices,customer);
			
		
	}
	
}
