package dk.cphbusiness.bank;

import java.util.Collection;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;
import javax.ejb.Singleton;

@Singleton
public class BankManager extends Observable {
  private final Map<String, Bank> banks = new ConcurrentHashMap<>();
  
  public BankManager() {
    banks.put("007", new Bank("007", "James Bond Bank", "http://bondagebanking.uk/Bond"));
    banks.put("4711", new Bank("4711", "Kurt og Sonjas Sparekasse", "http://kogs.dk/Sparekassen"));
    }
  
  public Collection<Bank> list() {
    return banks.values();
    }
  
  public Bank find(String reg) {
    return banks.get(reg);
    }
  
  public void save(Bank bank) {
    banks.put(bank.getReg(), bank);
    setChanged();
    notifyObservers();
    }
  
  public void drop(String reg) {
    banks.remove(reg);
    setChanged();
    notifyObservers();
    }
  
  
  
  }
