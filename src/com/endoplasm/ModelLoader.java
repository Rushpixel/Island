package com.endoplasm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ModelLoader {
	
	public static Model load(String path){
		System.out.println("loading model " + path);
		Model model = new Model();
		boolean HASNORMALS = false;
		boolean HASUV = false;
		try{
			InputStreamReader in = new InputStreamReader(ModelLoader.class.getResourceAsStream(path));
			BufferedReader br = new BufferedReader(in);
			
			String line = "";
			List<Vertex3f> v = new ArrayList<Vertex3f>(); 
			List<Vertex2f> vt = new ArrayList<Vertex2f>();  
			List<Vertex3f> vn = new ArrayList<Vertex3f>(); 
			List<Polygon> f = new ArrayList<Polygon>(); 
			
			while((line = br.readLine()) != null){
				String s[] = line.split(" ");
				if(s[0].equals("v")){
					v.add(new Vertex3f(Float.parseFloat(s[1]), Float.parseFloat(s[2]), Float.parseFloat(s[3])));
				} else if(s[0].equals("vt")){
					//flipped the v because it always seems to be wrong
					vt.add(new Vertex2f(Float.parseFloat(s[1]), 1 - Float.parseFloat(s[2])));
					HASUV = true;
				} else if(s[0].equals("vn")){
					vn.add(new Vertex3f(Float.parseFloat(s[1]), Float.parseFloat(s[2]), Float.parseFloat(s[3])));
					HASNORMALS = true;
				} else if(s[0].equals("f")){
					List<Vertex3i> pv = new ArrayList<Vertex3i>(); 
					for(int i = 1; i < s.length; i++){
						String c[] = s[i].split("/");
						// values have 1 subtracted to work with arrays, so that the first vertex has an index of 0
						pv.add(new Vertex3i(Integer.parseInt(c[0]) - 1, Integer.parseInt(c[1]) - 1, Integer.parseInt(c[2]) - 1));
					}
					Polygon p = new Polygon();
					p.v = new Vertex3i[pv.size()];
					pv.toArray(p.v);
					f.add(p);
				}
			}
			
			model.v = new Vertex3f[v.size()];
			model.vt = new Vertex2f[vt.size()];
			model.vn = new Vertex3f[vn.size()];
			model.f = new Polygon[f.size()];
			v.toArray(model.v);
			vt.toArray(model.vt);
			vn.toArray(model.vn);
			f.toArray(model.f);
			model.HASNORMALS = HASNORMALS;
			model.HASUV = HASUV;
			
		}catch(Exception e){
			e.printStackTrace();
			Endogen.exceptionhandler.ErrorMessage("Attempted to load broken Model", false);
			return null;
		}
		System.out.println("Model loaded succesfully");
		return model;
	}

}
