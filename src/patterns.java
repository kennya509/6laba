import java.util.function.Consumer;
import java.util.function.Supplier;

// Патерн Стратегія
class StrategyExample {
    static void executeStrategy(Runnable strategy) {
        strategy.run();
    }
}

// Патерн Фабричний метод
class Product {
    String name;

    public Product(String name) {
        this.name = name;
    }

    void show() {
        System.out.println("Продукт: " + name);
    }
}

class Factory {
    static Product create(Supplier<Product> factoryMethod) {
        return factoryMethod.get();
    }
}

// Патерн Декоратор
class Decorator {
    static Consumer<String> wrap(Consumer<String> base, Consumer<String> extra) {
        return text -> {
            base.accept(text);
            extra.accept(text);
        };
    }
}

// Патерн Навколишнє виконання (Execute Around)
class Resource implements AutoCloseable {
    void open() {
        System.out.println("Ресурс відкрито");
    }

    void work() {
        System.out.println("Робота з ресурсом");
    }

    public void close() {
        System.out.println("Ресурс закрито");
    }
}

class ExecuteAround {
    static void useResource(Consumer<Resource> action) {
        try (Resource resource = new Resource()) {
            resource.open();
            action.accept(resource);
        }
    }
}

public class patterns {
    public static void main(String[] args) {

        Runnable strategy1 = () -> System.out.println("Стратегія 1: проста дія");
        Runnable strategy2 = () -> System.out.println("Стратегія 2: інша дія");
        StrategyExample.executeStrategy(strategy1);
        StrategyExample.executeStrategy(strategy2);

        Product product = Factory.create(() -> new Product("Ноутбук"));
        product.show();

        Consumer<String> base = text -> System.out.println("Повідомлення: " + text);
        Consumer<String> extra = text -> System.out.println("Довжина: " + text.length());
        Consumer<String> combined = Decorator.wrap(base, extra);
        combined.accept("Привіт!");

        ExecuteAround.useResource(Resource::work);
    }
}
