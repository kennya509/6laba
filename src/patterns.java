import java.util.function.Consumer;
import java.util.function.Supplier;

public class patterns {

    interface Strategy {
        void execute();
    }

    static class ConcreteStrategyOne implements Strategy {
        @Override
        public void execute() {
            System.out.println("Стратегія 1 проста дія");
        }
    }

    static class ConcreteStrategyTwo implements Strategy {
        @Override
        public void execute() {
            System.out.println("Стратегія 2 інша дія");
        }
    }

    static class Context {
        private Strategy strategy;

        public Context(Strategy strategy) {
            this.strategy = strategy;
        }

        public void executeStrategy() {
            strategy.execute();
        }
    }

    interface Product {
        void show();
    }

    static class ConcreteProduct implements Product {
        private String name;

        public ConcreteProduct(String name) {
            this.name = name;
        }

        @Override
        public void show() {
            System.out.println("Продукт: " + name);
        }
    }

    static class Factory {
        public static Product createProduct(Supplier<Product> supplier) {
            return supplier.get();
        }
    }

    interface Notifier {
        void notify(String message);
    }

    static class BaseNotifier implements Notifier {
        @Override
        public void notify(String message) {
            System.out.println("Повідомлення: " + message);
        }
    }

    static class LengthNotifierDecorator implements Notifier {
        private Notifier notifier;

        public LengthNotifierDecorator(Notifier notifier) {
            this.notifier = notifier;
        }

        @Override
        public void notify(String message) {
            notifier.notify(message);
            System.out.println("Довжина: " + message.length());
        }
    }

    static class Resource implements AutoCloseable {
        public void open() {
            System.out.println("Ресурс відкрито");
        }

        public void work() {
            System.out.println("Робота з ресурсом");
        }

        @Override
        public void close() {
            System.out.println("Ресурс закрито");
        }
    }

    static class ResourceHelper {
        public static void useResource(Consumer<Resource> action) {
            try (Resource resource = new Resource()) {
                resource.open();
                action.accept(resource);
            }
        }
    }

    public static void main(String[] args) {
        Context context = new Context(new ConcreteStrategyOne());
        context.executeStrategy();

        context = new Context(new ConcreteStrategyTwo());
        context.executeStrategy();

        Product product = Factory.createProduct(() -> new ConcreteProduct("Ноутбук"));
        product.show();

        Notifier notifier = new BaseNotifier();
        Notifier decoratedNotifier = new LengthNotifierDecorator(notifier);
        decoratedNotifier.notify("Привіт!");

        ResourceHelper.useResource(Resource::work);
    }
}
