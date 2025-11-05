package restaurantes;


import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import java.util.Arrays;
import org.bson.Document;
import org.bson.conversions.Bson;

public class CafesManager {
    public static void main(String[] args) {
        MongoClient client = MongoClients.create("mongodb://localhost:27017/");
        MongoDatabase db = client.getDatabase("RestaurantsDB");
        MongoCollection<Document> cafes = db.getCollection("cafes");

        // insertar docuemtto
        Document cafePlaza = new Document("name", "Café de la Plaza")
                .append("stars", 4.3)
                .append("categories", Arrays.asList("Café", "Postres", "Desayuno"));
        cafes.insertOne(cafePlaza);

        //insertar varios docuemntos
        cafes.insertMany(Arrays.asList(
            new Document("name", "Espresso Express")
                .append("stars", 4.8)
                .append("categories", Arrays.asList("Café", "Rápido", "Takeaway")),
            new Document("name", "The Tea House")
                .append("stars", 3.9)
                .append("categories", Arrays.asList("Té", "Infusiones", "Postres")),
            new Document("name", "Morning Brew")
                .append("stars", 4.0)
                .append("categories", Arrays.asList("Café", "Desayuno", "Bakery"))
        ));

        //ponlerle filtros
        System.out.println("⭐ Cafés con stars >= 4.5");
        cafes.find(Filters.gte("stars", 4.5)).forEach(doc -> System.out.println(doc.toJson()));

        System.out.println("\n🔍 Nombre contiene 'Café'");
        cafes.find(Filters.regex("name", "Café")).forEach(doc -> System.out.println(doc.toJson()));

        System.out.println("\n🍰 Categoría incluye 'Postres'");
        cafes.find(Filters.in("categories", "Postres")).forEach(doc -> System.out.println(doc.toJson()));

        System.out.println("\n📊 Stars entre 3 y 4.3");
        cafes.find(Filters.and(Filters.gte("stars", 3), Filters.lte("stars", 4.3)))
             .forEach(doc -> System.out.println(doc.toJson()));

        System.out.println("\n🔠 Nombre empieza con 'T'");
        cafes.find(Filters.regex("name", "^T")).forEach(doc -> System.out.println(doc.toJson()));

        //actualiar
        cafes.updateOne(Filters.eq("name", "Morning Brew"), Updates.set("stars", 4.5));

        cafes.updateMany(
            Filters.in("categories", Arrays.asList("Bakery", "Desayuno")),
            Updates.inc("stars", 0.2)
        );

        cafes.updateOne(
            Filters.eq("name", "Café de la Plaza"),
            Updates.combine(
                Updates.set("phone", "555-111-2222"),
                Updates.set("open", true)
            )
        );

        //eliminar
        cafes.deleteOne(Filters.eq("name", "Espresso Express"));

        cafes.deleteMany(Filters.lt("stars", 4));

        cafes.deleteMany(Filters.in("categories", Arrays.asList("Takeaway", "Infusiones")));

        client.close();
    }
}
