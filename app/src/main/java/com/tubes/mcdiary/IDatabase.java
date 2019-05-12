package com.tubes.mcdiary;

import java.util.ArrayList;

/* Interface untuk DB dengan operasi :
   - add, remove, edit data.
   - Throws Exception jika input tidak valid.
 */
public interface IDatabase
{

    void addData(UserData data) throws Exception;


    void editData(UserData newData) throws Exception;


    void removeData(UserData data) throws Exception;

    ArrayList<UserData> getContent() throws Exception;
}
