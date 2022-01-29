/*
 * Copyright 2010 Srikanth Reddy Lingala  
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, 
 * software distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */

package in.nic.ease_of_living.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public  class AddFileZip {

	public static void Zip(String source, String destination)
	{

		/*if(!isBackup) {
			SQLiteDatabase db = SQLiteDatabase.openDatabase(source, null, 0);
			db.execSQL("DROP TABLE IF EXISTS population;");
			db.execSQL("DROP TABLE IF EXISTS lgd_village_m;");
			db.close();
			db.releaseReference();
		}*/


		byte[] buffer = new byte[1024];

		try{
			File file = new File(source);
			File file2 = new File(source+"-journal");

			FileOutputStream fos = new FileOutputStream(destination);
			ZipOutputStream zos = new ZipOutputStream(fos);
			ZipEntry ze= new ZipEntry(file.getName());
			zos.putNextEntry(ze);
			FileInputStream in = new FileInputStream(source);

			int len;
			while ((len = in.read(buffer)) > 0) {
				zos.write(buffer, 0, len);
			}


			in.close();
			zos.closeEntry();

			//remember close it
			zos.close();


			file.delete();
			file2.delete();

		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
}
