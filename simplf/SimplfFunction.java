package simplf;

import java.util.List;

class SimplfFunction implements SimplfCallable {
    private final Stmt.Function declaration;
    private final Environment closure;

    SimplfFunction(Stmt.Function declaration, Environment closure) {
        this.declaration = declaration;
        this.closure = closure;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> args) {
        Environment env = new Environment(closure);
        for (int i = 0; i < declaration.params.size(); i++) {
            env = env.define(declaration.params.get(i), 
                  declaration.params.get(i).lexeme, 
                  (i < args.size()) ? args.get(i) : null);
        }
        try {
            interpreter.executeBlock(declaration.body, env);
        } catch (Return returnValue) {
            return returnValue.value;
        }
        return null;
    }

    @Override
    public String toString() {
        return "<fn " + declaration.name.lexeme + ">";
    }
}