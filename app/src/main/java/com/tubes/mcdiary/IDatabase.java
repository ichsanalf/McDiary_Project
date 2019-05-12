package com.tubes.mcdiary;

import java.util.ArrayList;

public interface IDatabase
{

    void addData(UserData data) throws Exception;


    void editData(UserData newData) throws Exception;


    void removeData(UserData data) throws Exception;

    ArrayList<UserData> getContent() throws Exception;
}
