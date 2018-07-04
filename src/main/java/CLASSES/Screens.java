
package CLASSES;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Scanner;
import org.json.JSONObject;

public class Screens {
   
    public void homeScreen() throws IOException, URISyntaxException
    {
        Screens screen=new Screens();
        System.out.println("Welcome please in put the methode you want to use\n");
        System.out.println("1.Login\n2.Regestration");
        Scanner sc =new Scanner(System.in);
         int choice =sc.nextInt();
        switch (choice) {
            case 1:
                screen.LoginScreen();
                break;
            case 2:
                screen.RegestrationScreen();
                break;
            default:
                screen.homeScreen();
                break;
        }
    }
    public void LoginScreen() throws IOException, URISyntaxException
    {
        System.out.println("Enter You deatails to login as follows");    
        Screens screen=new Screens();
            Scanner sc=new Scanner(System.in);
            System.out.println("Enter Your User name");
            String username=sc.next();
            System.out.println("Enter Your password");
            String password=sc.next();
            
            Login in = new Login();
            String inResults = in.LoginAction(username, password);
            String SessionID = in.sessionID;
            
            if(inResults != null)
            {
                JSONObject jObject = new JSONObject(inResults);
                String UserName = (String) jObject.get("name");
                System.out.println("Welcome "+UserName+", please in put the methode you want to use");
                System.out.println("1.Scan Fingerprint\n2.Device list\n3.User list\n4.Mothly Log Events\n5.Search EventLogs\n6.Logout");
                int rollno1 = sc.nextInt();
                switch (rollno1) {
                    case 1:
                        {
                            System.out.println("Enter the device id number");
                            int deviceid=sc.nextInt();
                            int enrollQuality= 80;
                            System.out.println("Enroll Quality is set to "+enrollQuality+"");
                            System.out.println("Enter the the number of fingers to scan");
                            int finger =sc.nextInt();
                            System.out.println("you will be scanning "+finger+" fingerprints using the device with this id number :"+deviceid+"");
                            ScanFingerPrint lg = new ScanFingerPrint();
                            String scanresults = lg.ScanFingerNumber(finger, deviceid, enrollQuality,SessionID);
                            System.out.println("To enroll select\n 1.Yes\n2.No");
                            int selscted=sc.nextInt();
                            if(selscted == 1)
                            {
                                System.out.println("Enter the user national ID number for the one fingerprints have been scanned");
                                int nationalid=sc.nextInt();
                                EnrollFingerPrint enrl = new EnrollFingerPrint();
                                enrl.enroll(nationalid, scanresults,SessionID);
                            }else if(selscted == 2)
                            {
                                System.out.println("You have cancelled");
                                screen.LoginScreen();
                            }       break;
                        }
                    case 2:
                        {
                            AvailabeDeviceList devislist = new AvailabeDeviceList();
                            devislist.devislist(SessionID);
                            break;
                        }
                    case 3:
                        {
                            UsersList ulist = new UsersList();
                            ulist.userslist(SessionID);
                            break;
                        }
                    case 4:
                        {
                            AvailabeDeviceList devislist = new AvailabeDeviceList();
                            String List = devislist.devislist(SessionID);
                            EventTypesList eventtypeslist = new EventTypesList();
                            String eventtyperesults = eventtypeslist.eventstype(SessionID);
                            String[] ValuList = eventtypeslist.CodeListName(eventtyperesults);
                            MothlyLogEvent monthlylist = new MothlyLogEvent();
                            String[] DeviceList = monthlylist.DeviceQueryList(List);
                            monthlylist.logeventslist(ValuList, DeviceList, SessionID);
                            break;
                        }
                    case 5:
                        {
                            EventTypesList eventtypeslist = new EventTypesList();
                            String eventtyperesults = eventtypeslist.eventstype(SessionID);
                            String[] ValuList = eventtypeslist.CodeListName(eventtyperesults);
                            System.out.println("Enter the device id number");
                            int deviceid=sc.nextInt();
                            System.out.println("Enter Your Start Date");
                            String startdate=sc.next()+"T00:00:00.000z";
                            System.out.println("Enter Your Expiry Date");
                            String expdate=sc.next()+"T23:59:59.000z";
                            System.out.println("Here are the Details You have Entered");
                            System.out.println("1.device id : "+deviceid+"\n2.start date : "+startdate+"\n3.End date : "+expdate+"\n");
                            LogEventSearch ulist = new LogEventSearch();
                            ulist.logevents(deviceid, expdate, startdate, ValuList, SessionID);
                            break;
                        }
                    case 6:
                        {
                            
                            screen.homeScreen();
                            break;
                        }
                    default:
                        break;
                }
            }
    }
    public void RegestrationScreen() throws IOException, MalformedURLException, URISyntaxException
    {
        Screens screen=new Screens();
        Scanner sc =new Scanner(System.in);
        System.out.println("If all ready Register Enter\n 1.Yes\n2.No");
        int regestno =sc.nextInt();
        if(regestno == 1)
        {
            screen.homeScreen();
            
        }else if(regestno == 2)
        {
            
        System.out.println("Please input the valuse as asked to regester");
        
               System.out.println("Enter Your name");
               String name=sc.next();
               
               System.out.println("Enter Your User name");
               String username=sc.next();
               
               System.out.println("Enter Your national id");
               String nationalid=sc.next();
               System.out.println("Enter Your Email address");
               String Email=sc.next();
               
               System.out.println("Enter Your phone Number");
               String phoneNo=sc.next();
               
               System.out.println("Enter Your Start Date");
               String startdate=sc.next()+"T00:00:00.000z";
               
               System.out.println("Enter Your Expiry Date");
               String expdate=sc.next()+"T23:59:59.000z";
               
               System.out.println("Enter Your password");
               String password=sc.next();
               
               System.out.println("Enter Your operater level from list below");
               System.out.println("1.Administrator\n2.User Operator\n3.Monitoring operator\n4.T&A Operator\n5.User");
               int opno=sc.nextInt();
               String operator_name = null;
                switch (opno) {
                case 1:
                    operator_name = "Administrator";
                    break;
                case 2:
                    operator_name = "User Operator";
                    break;
                case 3:
                    operator_name = "Monitoring operator";
                    break;
                case 4:
                    operator_name = "T&A Operator";
                    break;
                case 5:
                    operator_name = "User";
                    break;
                default:
                    break;
                }
                System.out.println("Below are the access group list name and id");
                AccessGroupList acclist = new AccessGroupList();
                acclist.accesslist();
                
                System.out.println("Enter Your access groups id from the list above");
                int access_groups_id=sc.nextInt();
                
                System.out.println("Enter Your access groups name from the list above");
                String access_groups_name=sc.next();
                
                System.out.println("Below are the User group list name and id");
                UserGroupList usergroup = new UserGroupList();
                usergroup.usergrouplist();
                
                System.out.println("Enter Your user groups id from the list above");
                int user_group_id=sc.nextInt();
                
                System.out.println("Enter Your user groups name from the list above");
                String user_group_name=sc.next();
                
                System.out.println("You have inserted you Details as follows name :"+name+"\tusername :"+username+"\tnationalid :"+nationalid+"\t\n"
                        + "Email :"+Email+"\tstart date :"+startdate+"\texpiry date :"+expdate+"\n"
                        + "password :"+password+"\toperator name :"+operator_name+"\tphoneNo :"+phoneNo+"\n"
                        + "access_groups_id :"+access_groups_id+"\taccess_groups_name :"+access_groups_name+"\n"
                        + "user_group_id :"+user_group_id+"\tuser_group_name :"+user_group_name+"\n");
                
                System.out.println("Select Yes Or No for you To submit your regestration Details");
                System.out.println("1.Yes\n2.No");
                int submit = sc.nextInt();
                if(submit == 1)
                {
                    NewUser lg = new NewUser();
                    lg.adduser(Email, name, username, expdate, password, startdate,  nationalid, phoneNo, operator_name, user_group_id, user_group_name, access_groups_id, access_groups_name);
                }else if(submit == 1)
                {
                    System.out.println("You have cannceld your submition"); 
                    screen.homeScreen();
                }
        }
    }
}
