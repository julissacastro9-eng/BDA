/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.restaurants;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 *
 * @author Julissacastro
 */
public class Restaurants {

    public static void main(String[] args) {
        MongoClient client = MongoClients.create("mongodb://localhost:27017/");
        
        MongoDatabase db= client.getDatabase("RestaurantsDB");
        
        MongoCollection<Document> col = db.getCollection("Restaurants");
        
        
        Document document=  new Document();
        
        document.append("name", "Tacos de la Allende");
        document.append("stars", 4.5);
        
       // col.insertOne(document);
        
//        ArrayList<Document> lista = new ArrayList<>();
//        
//        lista.add(new Document("name", "Suchilito")
//                .append("stars", 5)
//                .append("categorias", Arrays.asList(new String []{"suchi","comida china", "comida rapida", "bebidas"}))
//        );
//        lista.add(new Document("name", "Deshuesadero")
//                .append("stars",3.5)
//                .append("categorias", Arrays.asList(new String []{"suchi","hamburguesas", "pizza", "bebidas"}))
//        );
//        
//        col.insertMany(lista);
//        
        
        for (Document d : col.find(Filters.eq("name","Sushilito"))) {
            System.out.println(d.toJson());
        }
        
        for (Document d : col.find(Filters.gt("stars",4))) {
            System.out.println(d.toJson());
        }
        
        
        
        Bson filtro =Filters.and(Filters.gte("stars", 3),Filters.lte("stars", 4));
        
        
        for (Document d : col.find(filtro)) {
            System.out.println(d.toJson());
        }
        
        System.out.println("-------------------------------------------");
        for (Document d : col.find(Filters.in("categorias", Arrays.asList("pizza")))) {
            System.out.println(d.toJson());
        }
        
        for (Document d : col.find(Filters.regex("categorias","o$"))) {
            System.out.println(d.toJson());
        }
        
        col.updateOne(Filters.eq("name","Suchilito"),Updates.set("stars", 1));
        
        
        col.updateOne(Filters.eq("id",new ObjectId("69095aaff9e6ee16dffaab42")), Updates.inc("stars", 1));
        
        
        //col.deleteOne(Filters.eq("id",new ObjectId("69095aaff9e6ee16dffaab42")));
        
        
    }
}
