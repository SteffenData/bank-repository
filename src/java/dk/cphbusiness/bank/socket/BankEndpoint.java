package dk.cphbusiness.bank.socket;

import dk.cphbusiness.bank.Bank;
import dk.cphbusiness.bank.BankManager;
import java.io.IOException;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/repository")
public class BankEndpoint implements Observer {
  private BankManager bankManager;
  private Session session;
  
  public BankEndpoint() {
    bankManager = lookupBankManagerBean();
    bankManager.addObserver(this);
    }
  
  @OnOpen
  public void onOpen(Session session) {
    this.session = session;
    update();
    }
  
  @OnMessage
  public String onMessage(String message) {
    return "Bank says "+message;
    }

  private BankManager lookupBankManagerBean() {
    try {
      Context c = new InitialContext();
      return (BankManager) c.lookup("java:global/BankRepository/BankManager!dk.cphbusiness.bank.BankManager");
    } catch (NamingException ne) {
      Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
      throw new RuntimeException(ne);
    }
  }

  @Override
  public void update(Observable o, Object arg) {
    update();
    }

  private void update() {  
    try {
      Collection<Bank> banks = bankManager.list();
      StringBuilder message = 
          new StringBuilder("<table class='BankList'><tr><th>Reg.</th><th>Name</th><th>Url</th></tr>");
      for (Bank bank : banks) {
        message
            .append("<tr><td>")
            .append(bank.getReg())
            .append("</td><td>")
            .append(bank.getName())
            .append("</td><td><a href='")
            .append(bank.getUrl())
            .append("'>")
            .append(bank.getUrl())
            .append("</a></td></tr>");
        }
      message.append("</table>");
      session.getBasicRemote().sendText(message.toString());
//      Set<Session> sessions = session.getOpenSessions();
//      String text = message.toString();
//      for (Session other : sessions) {
//        other.getBasicRemote().sendText(text);
//        }
      }
    catch (IOException ioe) {
      ioe.printStackTrace();
      }
    }
  
  }
