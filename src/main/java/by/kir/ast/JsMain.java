package by.kir.ast;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JsMain {

    public static void main(String[] args) {
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("nashorn");
        try {
            String expression = "2 + 2";
            engine.eval("var result = " + expression);
            Object result = engine.get("result");
            System.out.println("result = " + result);
        } catch (final ScriptException se) {
            se.printStackTrace();
        }
    }
}
