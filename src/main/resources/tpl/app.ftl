package app.customer;

import core.framework.module.App;
import core.framework.module.SystemModule;

/**
 * @author ebin
 */
public class CustomerServiceApp extends App {
    @Override
    protected void initialize() {
        load(new SystemModule("sys.properties"));
        load(new CustomerModule());
    }
}