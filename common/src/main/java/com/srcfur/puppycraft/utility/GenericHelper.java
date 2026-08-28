package com.srcfur.puppycraft.utility;

import com.srcfur.puppycraft.item.PuppyCraftItems;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class GenericHelper<T> {
    private Supplier<T> itemSupplier;
    private Identifier identifier;
    private T registered;
    public GenericHelper(Identifier identifier, Supplier<T> itemSupplier){
        this.identifier = identifier;
        this.itemSupplier = itemSupplier;
    }
    public void register(GenericHelper.Register<T> registerHandler){
        registered = registerHandler.register(identifier, itemSupplier);
    }

    public static <R> GenericHelper.Register<R> simpleRegisterHandler(Registry<R> registry){
        return (id, supplier) -> {
            return Registry.register(registry, id, supplier.get());
        };
    }

    @SuppressWarnings("unchecked")
    public static <T extends GenericHelper<? extends R>, R> void registerClass(Class<?> registeringClass, Class<T> type, GenericHelper.Register<R> function){
        Stream<Field> reflectedItems = Arrays.stream(registeringClass.getFields()).filter(x->x.getType() == type);
        reflectedItems.forEach(field ->{
            try {
                ((GenericHelper<R>)field.get(null)).register(function);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public T get() { return registered; };

    @FunctionalInterface
    public interface Register<T> {
        T register(Identifier id, Supplier<T> supplier);
    }
}
