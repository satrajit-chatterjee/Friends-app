package com.example.aditya.friends.utils;

import android.graphics.Bitmap;
import com.example.aditya.friends.api.OldPerson;
import java.util.ArrayList;
import com.example.aditya.friends.api.Appointment;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import com.example.aditya.friends.api.YoungPerson;

public class FriendsUtils {

    public final static String BASE_URL = "http://e7e482c7.ngrok.io";
    public final static int PERMISSION_ACCESS_FINE_LOCATION = 1001;

    public static OldPerson mOldPersonData = new OldPerson();
    public static ArrayList<Appointment> appointmentArrayList = new ArrayList<>();

    public static void addAppointment(Appointment appointment){
        appointmentArrayList.add(appointment);
    }

    public static String getAgeFromBirthday(String birthday) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(birthday + " 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date(60 * 31556952000L);
        }
        long ageMilliseconds = System.currentTimeMillis() - date.getTime();
        return (ageMilliseconds / 31556952000L) + "";
    }

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public static byte[] getByteFromBitmap(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        bitmap.recycle();
        return byteArray;
    }

    public static ArrayList<YoungPerson> youngPeople = new ArrayList<>();


    public static void addDummy4(){
        ArrayList<String> temp = new ArrayList<>();

        YoungPerson youngPerson = new YoungPerson();

        youngPerson.setBirthday("31/12/1999");
        youngPerson.setName("Ishani Srivastava");
        temp.clear();
        temp.add("Cooking");
        temp.add("Baking");
        temp.add("Movie");
        temp.add("Picnic");
        youngPerson.setInterests(temp);
        youngPerson.setContactNumber("8762998109");
        youngPerson.setGender("M");
        youngPerson.setGenderPreference("A");
        youngPerson.setLatitude(10.23);
        youngPerson.setLatitude(78.89);
        youngPerson.setProfileImageUrl("https://static1.bigstockphoto.com/9/5/9/large1500/95980166.jpg");
        youngPerson.setPassword("yygwfe");
        youngPerson.setUniqueId(UUID.randomUUID().toString());
        youngPeople.add(youngPerson);

    }
    public static void addDummy5(){
        ArrayList<String> temp = new ArrayList<>();

        YoungPerson youngPerson = new YoungPerson();
        youngPerson.setBirthday("28/04/1997");
        youngPerson.setName("Saikat Kar");
        temp.clear();
        temp.add("Cooking");
        temp.add("Movie");
        temp.add("Picnic");
        temp.add("Guitar");
        youngPerson.setInterests(temp);
        youngPerson.setContactNumber("7823561209");
        youngPerson.setGender("F");
        youngPerson.setGenderPreference("A");
        youngPerson.setLatitude(10.23);
        youngPerson.setLatitude(78.89);
        youngPerson.setProfileImageUrl("https://s3.india.com/wp-content/uploads/2018/05/College-students.jpg");
        youngPerson.setPassword("abcjfgh");
        youngPerson.setUniqueId(UUID.randomUUID().toString());
        youngPeople.add(youngPerson);


    }
    public static void addDummy3(){
        ArrayList<String> temp = new ArrayList<>();

        YoungPerson youngPerson = new YoungPerson();
        youngPerson.setBirthday("23/07/1995");
        youngPerson.setName("Satrajit Chatterjee");
        temp.clear();
        temp.add("Swimming");
        temp.add("Baking");
        temp.add("Cricket");
        temp.add("Picnic");
        temp.add("Guitar");
        youngPerson.setInterests(temp);
        youngPerson.setContactNumber("8235432109");
        youngPerson.setGender("M");
        youngPerson.setGenderPreference("A");
        youngPerson.setLatitude(10.23);
        youngPerson.setLatitude(78.89);
        youngPerson.setProfileImageUrl("https://www.collegeincolorado.org/images/cic/new/home/college.jpg");
        youngPerson.setPassword("kshgdaf");
        youngPerson.setUniqueId(UUID.randomUUID().toString());
        youngPeople.add(youngPerson);
    }
    public static void addDummy1(){
        ArrayList<String> temp = new ArrayList<>();

        YoungPerson youngPerson = new YoungPerson();
        youngPerson.setBirthday("23/07/1995");
        youngPerson.setName("Adwaith AD");
        temp.clear();
        temp.add("Swimming");
        temp.add("Baking");
        temp.add("Cricket");
        temp.add("Picnic");
        temp.add("Guitar");
        youngPerson.setInterests(temp);
        youngPerson.setContactNumber("8235432109");
        youngPerson.setGender("M");
        youngPerson.setGenderPreference("A");
        youngPerson.setLatitude(10.23);
        youngPerson.setLatitude(78.89);
        youngPerson.setProfileImageUrl("https://c8.alamy.com/comp/ARM485/portrait-of-beautiful-indian-college-student-on-campus-ARM485.jpg");
        youngPerson.setPassword("kshgdaf");
        youngPerson.setUniqueId(UUID.randomUUID().toString());
        youngPeople.add(youngPerson);
    }
    public static void addDummy2(){
        ArrayList<String> temp = new ArrayList<>();

        YoungPerson youngPerson = new YoungPerson();
        youngPerson.setBirthday("28/07/2002");
        youngPerson.setName("Akash Preetam");
        temp.clear();
        temp.add("Cooking");
        temp.add("Movie");
        temp.add("Guitar");
        youngPerson.setInterests(temp);
        youngPerson.setContactNumber("9876543210");
        youngPerson.setGender("M");
        youngPerson.setGenderPreference("A");
        youngPerson.setLatitude(10.23);
        youngPerson.setLatitude(78.89);
        youngPerson.setProfileImageUrl("http://images.archant.co.uk/polopoly_fs/1.5644097.1533802699!/image/image.jpg_gen/derivatives/landscape_630/image.jpg");
        youngPerson.setPassword("wefigqw");
        youngPerson.setUniqueId(UUID.randomUUID().toString());
        youngPeople.add(youngPerson);

    }

}

