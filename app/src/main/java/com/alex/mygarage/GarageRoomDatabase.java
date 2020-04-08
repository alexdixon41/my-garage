package com.alex.mygarage;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.alex.mygarage.models.Vehicle;
import com.alex.mygarage.models.VehicleField;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Vehicle.class, VehicleField.class}, version = 6, exportSchema = false)
public abstract class GarageRoomDatabase extends RoomDatabase {

    public abstract GarageDao garageDao();

    private static volatile GarageRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static GarageRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GarageRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            GarageRoomDatabase.class, "vehicle_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                GarageDao dao = INSTANCE.garageDao();

                Vehicle vehicle = new Vehicle();
                vehicle.setName("2003 Ford Mustang GT");
                vehicle.setYear("2003");
                vehicle.setMake("Ford");
                vehicle.setModel("Mustang GT");
                vehicle.setTrim("Premium");
                vehicle.setBodyType("Coupe");
                vehicle.setDoors("2-Door");
                vehicle.setColor("Satin Silver");
                vehicle.setDriveType("Rear-Wheel Drive");

                vehicle = new Vehicle();
                vehicle.setName("2006 Subaru Forester");
                vehicle.setYear("2006");
                vehicle.setMake("Subaru");
                vehicle.setModel("Forester");
                vehicle.setTrim("X Premium");
                vehicle.setBodyType("Wagon");
                vehicle.setDoors("4-door");
                vehicle.setColor("Regal Blue Pearl");
                vehicle.setDriveType("All-Wheel Drive");
                vehicle.setGeneralOther("Poobaru");
                dao.insertVehicle(vehicle);
                vehicle = new Vehicle();
                vehicle.setName("2001 Honda Prelude");
                vehicle.setYear("2001");
                vehicle.setMake("Honda");
                vehicle.setModel("Prelude");
                vehicle.setTrim("Base");
                vehicle.setBodyType("Coupe");
                vehicle.setDoors("2-Door");
                vehicle.setColor("Nighthawk Black Pearl");
                vehicle.setDriveType("Front-Wheel Drive");
                vehicle.setGeneralOther("Smokey");
                dao.insertVehicle(vehicle);
                vehicle = new Vehicle();
                vehicle.setName("2001 Mazda Tribute");
                vehicle.setYear("2001");
                vehicle.setMake("Mazda");
                vehicle.setModel("Tribute");
                vehicle.setTrim("ES");
                vehicle.setBodyType("SUV");
                vehicle.setDoors("4-Door");
                vehicle.setColor("Maroon");
                vehicle.setDriveType("Front/Four-Wheel Drive");
                vehicle.setGeneralOther("Rusty");
                dao.insertVehicle(vehicle);
                vehicle = new Vehicle();
                vehicle.setName("1999 Ford F250");
                vehicle.setYear("1999");
                vehicle.setMake("Ford");
                vehicle.setModel("F250");
                vehicle.setTrim("Lariat Offroad 4x4");
                vehicle.setBodyType("SuperCab Pickup");
                vehicle.setDoors("4-Door");
                vehicle.setColor("Oxford White");
                vehicle.setDriveType("Rear/Four-Wheel Drive");
                vehicle.setGeneralOther("Trusty");
                dao.insertVehicle(vehicle);
                vehicle = new Vehicle();
                vehicle.setName("2003 Ford Mustang Cobra");
                vehicle.setYear("2003");
                vehicle.setMake("Ford");
                vehicle.setModel("Mustang SVT Cobra");
                vehicle.setTrim("Premium");
                vehicle.setBodyType("Coupe");
                vehicle.setDoors("2-Door");
                vehicle.setColor("Sonic Blue");
                vehicle.setDriveType("Rear-Wheel Drive");
                vehicle.setGeneralOther("Epic 4V Supercharged");
                dao.insertVehicle(vehicle);
            });

        }
    };
}
