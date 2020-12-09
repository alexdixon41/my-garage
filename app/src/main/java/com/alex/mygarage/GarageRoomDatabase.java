package com.alex.mygarage;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.alex.mygarage.models.Component;
import com.alex.mygarage.models.ComponentField;
import com.alex.mygarage.models.Vehicle;
import com.alex.mygarage.models.VehicleField;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Vehicle.class, VehicleField.class, Component.class, ComponentField.class}, version = 9, exportSchema = false)
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
                vehicle.setId(1);
                dao.insertVehicle(vehicle);

                Component general = new Component();
                general.setId(1);
                general.setIconName("");
                general.setName("General");
                general.setVehicleId(1);
                dao.insertVehicleComponent(general);

                ComponentField year = new ComponentField();
                year.setCid(1);
                year.setName("year");
                year.setValue("2003");
                dao.insertComponentField(year);

                ComponentField make = new ComponentField();
                make.setCid(1);
                make.setName("make");
                make.setValue("Ford");
                dao.insertComponentField(make);

                ComponentField model = new ComponentField();
                model.setCid(1);
                model.setName("model");
                model.setValue("Mustang");
                dao.insertComponentField(model);

                ComponentField trim = new ComponentField();
                trim.setCid(1);
                trim.setName("trim");
                trim.setValue("GT");
                dao.insertComponentField(trim);

                ComponentField driveType = new ComponentField();
                driveType.setCid(1);
                driveType.setName("drive type");
                driveType.setValue("RWD");
                dao.insertComponentField(driveType);

                ComponentField g = new ComponentField();
                g.setCid(1);
                g.setName("exterior color");
                g.setValue("Satin Silver");
                dao.insertComponentField(g);

                g = new ComponentField();
                g.setCid(1);
                g.setName("interior color");
                g.setValue("Charcoal Black");
                dao.insertComponentField(g);

                Component engine = new Component();
                engine.setId(2);
                engine.setIconName("ic_engine_icon_vector");
                engine.setName("Engine");
                engine.setVehicleId(1);
                dao.insertVehicleComponent(engine);

                ComponentField disp = new ComponentField();
                disp.setCid(2);
                disp.setName("displacement");
                disp.setValue("4.6L");
                dao.insertComponentField(disp);

                ComponentField valves = new ComponentField();
                valves.setCid(2);
                valves.setName("valves per cylinder");
                valves.setValue("2");
                dao.insertComponentField(valves);

                ComponentField config = new ComponentField();
                config.setCid(2);
                config.setName("configuration");
                config.setValue("V8");
                dao.insertComponentField(config);

                ComponentField cylinderHeadConfig = new ComponentField();
                cylinderHeadConfig.setCid(2);
                cylinderHeadConfig.setName("cylinder head configuration");
                cylinderHeadConfig.setValue("SOHC");
                dao.insertComponentField(cylinderHeadConfig);

                ComponentField e = new ComponentField();
                e.setCid(2);
                e.setName("aspiration type");
                e.setValue("Naturally Aspirated");
                dao.insertComponentField(e);

                e = new ComponentField();
                e.setCid(2);
                e.setName("manufacturing plant");
                e.setValue("Romeo");
                dao.insertComponentField(e);

                e = new ComponentField();
                e.setCid(2);
                e.setName("bore");
                e.setValue("3.552 in (90.2 mm)");
                dao.insertComponentField(e);

                e = new ComponentField();
                e.setCid(2);
                e.setName("stroke");
                e.setValue("3.543 in (90.0 mm)");
                dao.insertComponentField(e);

                e = new ComponentField();
                e.setCid(2);
                e.setName("deck height");
                e.setValue("8.937 in (227.0 mm)");
                dao.insertComponentField(e);

                e = new ComponentField();
                e.setCid(2);
                e.setName("connecting rod length");
                e.setValue("5.933 in (150.7mm)");
                dao.insertComponentField(e);

                e = new ComponentField();
                e.setCid(2);
                e.setName("manufacturing plant");
                e.setValue("Romeo");
                dao.insertComponentField(e);


                Component trans = new Component();
                trans.setId(3);
                trans.setIconName("ic_transmission_icon_vector");
                trans.setName("Transmission");
                trans.setVehicleId(1);
                dao.insertVehicleComponent(trans);

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
