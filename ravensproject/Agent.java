package ravensproject;

//import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
//import javax.swing.ImageIcon;
//import javax.swing.JFrame;
//import javax.swing.JLabel;

// Uncomment these lines to access image processing.
//import java.awt.Image;
//import java.io.File;
//import javax.imageio.ImageIO;

/**
 * Your Agent for solving Raven's Progressive Matrices. You MUST modify this
 * file.
 * 
 * You may also create and submit new files in addition to modifying this file.
 * 
 * Make sure your file retains methods with the signatures:
 * public Agent()
 * public char Solve(RavensProblem problem)
 * 
 * These methods will be necessary for the project's main method to run.
 * 
 */
public class Agent {
    /**
     * The default constructor for your Agent. Make sure to execute any
     * processing necessary before your Agent starts solving problems here.
     * 
     * Do not add any variables to this signature; they will not be used by
     * main().
     * 
     */
    public Agent() {
        
    }
    
    private static void returnPermutes(ArrayList<String> l1, ArrayList<String> l2, HashSet<HashSet<String>> finalList, HashSet<String> tempList , int size) {
		if(tempList.size()==size){
			finalList.add((HashSet<String>)tempList.clone());
			return;
		}
		for (String a : l1){
			for(String b:l2){
				tempList.add(a+"-"+b);
				ArrayList<String> newL1 = (ArrayList<String>)l1.clone();
				ArrayList<String> newL2 = (ArrayList<String>)l2.clone();
				newL1.remove(a);
				newL2.remove(b);
				//System.out.println(tempList.toString());
				returnPermutes(newL1,newL2, finalList,tempList,size);
				tempList.remove(a+"-"+b);
				
			}
		}
		//System.out.println(finalList.toString());
	}

    public int returnAnswer(HashMap<String, RavensObject> objectsA, HashMap<String, RavensObject> objectsB, HashMap<String, RavensObject> objectsC, 
    		HashMap<String, RavensObject> objects1,HashMap<String, RavensObject> objects2, HashMap<String, RavensObject> objects3, HashMap<String, RavensObject> objects4, 
    		HashMap<String, RavensObject> objects5, HashMap<String, RavensObject> objects6, 
    		HashMap<String, RavensObject> objects7, HashMap<String, RavensObject> objects8, boolean flag){
    		
    		//System.out.println("RETURN ANSWER");
    	
			HashMap<String, String> aTob = convertToRavensObject(objectsA, objectsB, flag);
			//System.out.println("********");
			HashMap<String, String> cTo1 = convertToRavensObject(objectsC, objects1, flag);
			//System.out.println("********");
			HashMap<String, String> cTo2 = convertToRavensObject(objectsC, objects2, flag);
			//System.out.println("********");
			HashMap<String, String> cTo3 = convertToRavensObject(objectsC, objects3, flag);

			HashMap<String, String> cTo4 = convertToRavensObject(objectsC, objects4, flag);
			HashMap<String, String> cTo5 = convertToRavensObject(objectsC, objects5, flag);
			HashMap<String, String> cTo6 = convertToRavensObject(objectsC, objects6, flag);
			HashMap<String, String> cTo7 = null;
			HashMap<String, String> cTo8 = null;
			if(objects7 != null && objects8 != null){
				cTo7 = convertToRavensObject(objectsC, objects7, flag);
				cTo8 = convertToRavensObject(objectsC, objects8, flag);
			}
			
			//System.out.println("AtoB DIFFERENCES! " + aTob.toString() + "\n");
			
			//System.out.println("GET DIFFERENCES!");
			
			int diff1 = getDiff(aTob, cTo1);
			//System.out.println("Diff1: " + diff1);
			int diff2 = getDiff(aTob, cTo2);
			//System.out.println("Diff2: " + diff2);
			int diff3 = getDiff(aTob, cTo3);
			//System.out.println("Diff3: " + diff3);
			int diff4 = getDiff(aTob, cTo4);
			//System.out.println("Diff4: " + diff4 + "\n");
			int diff5 = getDiff(aTob, cTo5);
			//System.out.println("Diff5: " + diff5);
			int diff6 = getDiff(aTob, cTo6);
			//System.out.println("Diff6: " + diff6);
			
			
			HashMap<Integer, Integer> getAnswer = new HashMap<Integer, Integer>();
			
			
			if(objects7 != null && objects8 != null){
				int diff7 = getDiff(aTob, cTo7);
				//System.out.println("Diff7: " + diff7);
				int diff8 = getDiff(aTob, cTo8);
				//System.out.println("Diff8: " + diff8);
				getAnswer.put(7, diff7);
				getAnswer.put(8, diff8);				
			}
			
			
			getAnswer.put(1, diff1);
			getAnswer.put(2, diff2);
			getAnswer.put(3, diff3);
			getAnswer.put(4, diff4);
			getAnswer.put(5, diff5);
			getAnswer.put(6, diff6);
			
			System.out.println(getAnswer.toString());
			
			Entry<Integer, Integer> minEntry = null;
			int count = 0;
			for(Map.Entry<Integer, Integer> entry : getAnswer.entrySet()){
				if (minEntry == null || entry.getValue().compareTo(minEntry.getValue()) < 0)
			    {
					//System.out.println("Return Answer!!!!! " + minEntry);
			        minEntry = entry;
			    } 
			}
			
			for(Map.Entry<Integer, Integer> entry : getAnswer.entrySet()){
				if(minEntry != null && entry.getValue().compareTo(minEntry.getValue()) == 0){
			    	count++;
			    }
			}
			
//			if(count>1){
//				return -1;
//			}
			
			System.out.println("MINENTRY: " + minEntry.getKey());
			return minEntry.getKey();
    	
    }
    
    public HashMap<String,String> transformAtoB(HashMap<String,String> attributesA, HashMap<String,String> attributesB, boolean flag){
    	//System.out.println("in transform");
    	
    	HashMap<String,String> difference = new HashMap<String,String>(); //map of transformations
    	
    	HashSet<String> allKeys = new HashSet<String>();
    
    	//System.out.println("AttributesA: " + attributesA.keySet());
    	for (String attribute: attributesA.keySet()){
    		
    		//System.out.println(attribute);
    		allKeys.add(attribute);
    		
        	//HashMap<String,String> difference = new HashMap<String,String>(); //map of transformations 	
    		
    		if(attribute.equals("angle") && attributesB.containsKey(attribute)){
    			//System.out.println("failing angle");
    			int angleDiff = Integer.parseInt(attributesA.get(attribute)) - Integer.parseInt(attributesB.get(attribute));	
				angleDiff = Math.abs(angleDiff);
				if(angleDiff != 0){
					difference.put(attribute, String.valueOf(angleDiff));
				}
    		
    		} else if(attribute.equals("size") && !attributesB.containsKey(attribute)){
    			if(attributesB.containsKey("width") && attributesB.containsKey("height")){
    				attributesA.put("width", attributesA.get("size"));
    				attributesA.put("height", attributesA.get("size"));
    				difference.put("width", attributesA.get("width") + "-" + attributesB.get("width"));
    				difference.put("height", attributesA.get("height") + "-" + attributesB.get("height"));
    				attributesA.remove("size");
    				attributesA.put("shape", attributesB.get("shape"));
    			}
    			
    		} else if(attribute.equals("width")  && !attributesB.containsKey(attribute)){
    			
    			if(attributesB.containsKey("size")){
    				attributesB.put("width", attributesB.get("size"));
    				attributesB.put("height", attributesB.get("size"));
    				//System.out.println(attributesB.toString());
    				difference.put(attribute, attributesA.get(attribute) + "-" + attributesB.get(attribute));
    				difference.put("height", attributesA.get("height") + "-" + attributesB.get("height"));
    				attributesB.remove("size");
    				attributesB.put("shape", attributesA.get("shape"));
    				//System.out.println(difference.toString());
    			}
    	
    		} 
    		else if((attributesA.get("shape").equals("circle") && attributesB.get("shape").equals("circle")) || 
    				attributesA.get("shape").equals("diamond") && attributesB.get("shape").equals("diamond")){
    			if(attribute.equals("left-of") && !attributesB.containsKey(attribute)){
    				
    				difference.put(attributesA.get("shape"), "right");
    			}
    		}	
    		
    		else if(attribute.equals("alignment")) {
    			//System.out.println("failing here");
    			if (attributesA.get(attribute).contains("bottom") && attributesB.get(attribute).contains("bottom") || 
    					attributesA.get(attribute).contains("top") && attributesB.get(attribute).contains("top")){
    				
    				if(attributesA.get(attribute).contains("right")){
    					if (attributesB.get(attribute).contains("right")){
    						difference.put(attribute, "right-right");
    					} else {
    						difference.put(attribute, "right-left");
    					}
    						
    				}
    					
    			}
			} 
    		else if((attributesA.containsKey("left-of") && !attributesB.containsKey(attribute)) && attributesB.containsKey("overlaps")){
    			difference.put("move", "right");
    		}
    		else if((attributesA.containsKey("overlaps") && !attributesB.containsKey(attribute)) && !attributesB.containsKey("left-of")){
    			difference.put("move", "right");
    		}
//    		else if((attributesA.containsKey("overlaps") && !attributesB.containsKey(attribute)) && attributesB.containsKey("right-of")){
//    			difference.put("move", "left");
//    		}
    		else if((attributesA.containsKey("overlaps") && attributesB.containsKey("left-of"))){
    			difference.put("move", "left");
    		} else if(attribute.startsWith("left-of") || attribute.startsWith("above")){
				continue;
			}
    		
    		else if((!attributesB.containsKey(attribute) || !attributesA.get(attribute).equals(attributesB.get(attribute)))){
    			difference.put(attribute,  attributesA.get(attribute) + "-" + attributesB.get(attribute));
    			//System.out.println("DIFF: " + difference.toString());
    		} 
//    		else if(attributesA.containsKey(attribute) && attributesB.containsKey(attribute)){
//    			//System.out.println("*****"+attribute + " - change : "+ attributesA.get(attribute) +" ," + attributesB.get(attribute));
//    			//System.out.println("DIFFERENCE: " + attribute);
//    			difference.put(attribute,  attributesA.get(attribute) + "-" + attributesB.get(attribute));
//    		}
    	}
    	
    	
    	for (String attribute: attributesB.keySet()){
    		if(allKeys.contains(attribute)){
    			continue;
    		} else {
	    		if(attribute.equals("angle")  && attributesA.containsKey(attribute)){
	    			//System.out.println("failing angle");
	    			int angleDiff = Integer.parseInt(attributesA.get(attribute)) - Integer.parseInt(attributesB.get(attribute));
	    			if(flag){
	    				
	        			int temp = (180 - Integer.parseInt(attributesA.get(attribute)))%360;	
	        			
	        			if (temp == Integer.parseInt(attributesB.get(attribute))){
	        				difference.put(attribute, "reflection");	//it's a reflection
	        				//System.out.println(difference.toString());
	        			} else {
	        				angleDiff = Math.abs(angleDiff);
	        				difference.put(attribute, String.valueOf(angleDiff));
	        			}
	
	    			}
	    			
					angleDiff = Math.abs(angleDiff);
					if(angleDiff != 0){
						difference.put(attribute, String.valueOf(angleDiff));
					}
	    		}
	    		else if(attribute.equals("size")){
					if(attributesA.containsKey("width") && attributesA.containsKey("height")){
						attributesB.put("width", attributesB.get("size"));
						attributesB.put("height", attributesB.get("size"));
						difference.put("width", attributesA.get("width") + "-" + attributesB.get("width"));
	    				difference.put("height", attributesA.get("height") + "-" + attributesB.get("height"));
	    				attributesB.remove("size");
	    				attributesB.put("shape", attributesB.get("shape"));
					}
	    		} 
	    		else if(attribute.equals("width")  && !attributesA.containsKey(attribute)){
	    			if(attributesA.containsKey("size")){
	    				attributesA.put("width", attributesA.get("size"));
	    				attributesA.put("height", attributesA.get("size"));	
	    				difference.put(attribute, attributesB.get(attribute) + "-" + attributesA.get(attribute));
	    				difference.put("height", attributesB.get("height") + "-" + attributesA.get("height"));
	    				attributesA.remove("size");
	    				attributesA.put("shape", attributesA.get("shape"));
	    			}
	    		} 
	    		else if((attributesA.get("shape").equals("circle") && attributesB.get("shape").equals("circle")) || 
	    				attributesA.get("shape").equals("diamond") && attributesB.get("shape").equals("diamond")){
	    			if(attribute.equals("left-of") && !attributesA.containsKey(attribute)){
	    				difference.put(attributesA.get("shape"), "left");
	    			}
	    		}	
//	    		else if(attribute.equals("left-of") && !attributesA.containsKey(attribute) ){
//	    			difference.put("move", "left");
//	    		}
//	    		else if((attributesA.containsKey("overlaps") && !attributesB.containsKey(attribute))){
//	    			difference.put("move", "right");
//	    		} 
	    		else if(attribute.startsWith("left-of") || attribute.startsWith("above")){
					continue;
				}
	    		else if(attribute.equals("alignment")) {
	    			//System.out.println("failing here");
	    			if (attributesA.get(attribute).contains("bottom") && attributesB.get(attribute).contains("bottom") || 
	    					attributesA.get(attribute).contains("top") && attributesB.get(attribute).contains("top")){	
	    				if(attributesA.get(attribute).contains("right")){
	    					if (attributesB.get(attribute).contains("right")){
	    						difference.put(attribute, "right-right");
	    					} else {
	    						difference.put(attribute, "right-left");
	    					}
	    						
	    				}
	    					
	    			}
				}
	    		else if((!attributesA.containsKey(attribute) || !attributesA.get(attribute).equals(attributesB.get(attribute)))){
	    			difference.put(attribute,  attributesA.get(attribute) + "-" + attributesB.get(attribute));
	    		} 
    		}
    	}
    
    	
    	//System.out.println("Final Difference: " + difference.toString());
    	return difference;
    }

    public int getDiff(HashMap<String,String> differenceAtoB, HashMap<String,String> differenceCtoD){
//    	System.out.println("DIFF AToB: " + differenceAtoB.toString());
//    	System.out.println("DIFF CtoD: " + differenceCtoD.toString());
    	

    	
    	HashMap<String, Integer> points = new HashMap<String, Integer>();
    	
    	points.put("very small", 0);
    	points.put("small",1);
    	points.put("medium",2);
    	points.put("large", 3);
    	points.put("very large",4);
    	points.put("huge",5);

    	
    	int difference1 = 0;
    	int difference2 = 0;
    	//HashSet<String> set = new HashSet<String>();
    	
    	
    	int difference=0;
    	for(String key : differenceAtoB.keySet()){
    		//System.out.println(key);
    		if((key.startsWith("insertion") || key.startsWith("deletion")) && (!differenceCtoD.containsKey(key))){
    			difference +=4;
    			//System.out.println("Difference!!!: " + difference);
//    		} else if(key.equals("shape") && differenceAtoB.get(key).equals("square-rectangle") || differenceAtoB.get(key).equals("rectangle-square")){
    			
//    		} else if(key.startsWith("left-of") || key.startsWith("overlaps") || key.startsWith("above")){
//    			continue;
//    		} else if(key.equals("left-of") && !differenceCtoD.containsKey("left-of")){
//    			String diff = differenceAtoB.get(key);
    		}
    		else if(key.startsWith("fill") && differenceCtoD.containsKey(key)){
    			if (!differenceAtoB.get(key).equals(differenceCtoD.get(key))){
    				
    				
//    				System.out.println(differenceCtoD.get(key));
//    				System.out.println("diff before: "+ difference);
    				difference +=2;
//    				System.out.println("diff after: "+ difference);
    			}
    		}
    		else if((key.equals("size") || key.equals("width") || key.equals("height")) && differenceCtoD.containsKey(key)){
    			difference1 = points.get(differenceAtoB.get(key).substring(0,differenceAtoB.get(key).indexOf('-'))) - points.get(differenceAtoB.get(key).substring(differenceAtoB.get(key).indexOf('-')+1));
    			difference2 = points.get(differenceCtoD.get(key).substring(0,differenceCtoD.get(key).indexOf('-'))) - points.get(differenceCtoD.get(key).substring(differenceCtoD.get(key).indexOf('-')+1));
    			difference += Math.abs(difference1 - difference2);
//    		} else if(key.startsWith("left-of") && differenceCtoD.containsKey(key)){
//    			difference += Math.abs(Integer.parseInt(differenceAtoB.get(key))  - Integer.parseInt(differenceCtoD.get(key)));
    		} else if((key.equals("size") || key.equals("width") || key.equals("height")) && !differenceCtoD.containsKey(key)){
    			difference += Math.abs(points.get(differenceAtoB.get(key).substring(0,differenceAtoB.get(key).indexOf('-'))) - points.get(differenceAtoB.get(key).substring(differenceAtoB.get(key).indexOf('-')+1)));
    		}
    		else if (key.startsWith("left-of") || key.startsWith("overlaps") || key.startsWith("above") || key.startsWith("inside")){
    			continue;
    		} else if (key.equals("circle") && differenceCtoD.containsKey(key) || (key.equals("diamond") && differenceCtoD.containsKey(key))){
    			if(differenceAtoB.get(key) != differenceCtoD.get(key)){
    				//System.out.println("CIRCLE POSITION");
    				difference +=2;
    			}
    		} 
    		else if(!differenceCtoD.containsKey(key) || !differenceCtoD.get(key).equals(differenceAtoB.get(key))){
    			difference +=1;
    		}
    	}
    	
    	
    	for(String key : differenceCtoD.keySet()){
//    		if(differenceAtoB.keySet().contains(key)){
//    			continue;
//    		}
    		
    		if((key.startsWith("insertion") || key.startsWith("deletion")) && (!differenceAtoB.containsKey(key))){
    			difference +=4;
//    		} else if(key.equals("shape") && differenceCtoD.get(key).equals("square-rectangle") || differenceCtoD.get(key).equals("rectangle-square")){
    						
    		} 
    		else if(key.startsWith("overlaps") || key.startsWith("above")){
    			continue;
    		}  else if (key.equals("diamond") && differenceAtoB.containsKey("circle")){
    			if(differenceCtoD.get("diamond") != differenceAtoB.get("circle")){
    				continue;
    			} else {
    				difference += 2;
    			}
    		}
    		else if((key.equals("size") || key.equals("width") || key.equals("height")) && !differenceAtoB.containsKey(key)){
    			difference += Math.abs(points.get(differenceCtoD.get(key).substring(0,differenceCtoD.get(key).indexOf('-'))) - points.get(differenceCtoD.get(key).substring(differenceCtoD.get(key).indexOf('-')+1)));
    		}
    		
    		
    		else if(!differenceAtoB.containsKey(key)){
    			difference +=1;
    		}
    	}
    	return difference;
    	//return map.put(key, value);
    }
    
    public HashMap<String, String> getTransAtoB(HashMap<RavensObject,RavensObject> map){
    	HashMap<String, String> ret = new HashMap<String, String>();

    	boolean reflection = false;
    	for(RavensObject obj : map.keySet()){
    		//HashMap<String,String> val = transform(map.get(obj));
    		//HashMap<String, String> temp = transform(obj.getAttributes(), map.get(obj).getAttributes()); 
    		if(obj.getAttributes().containsKey("angle") && map.get(obj).getAttributes().containsKey("angle")){
    			//int temp2 = 540 - Integer.parseInt(obj.getAttributes().get("angle"));
    			int temp = (180 - Integer.parseInt(obj.getAttributes().get("angle")))%360;		
    			if (temp == Integer.parseInt(map.get(obj).getAttributes().get("angle"))){
    				reflection = true;
    			}
    			
    		}
    		
    		HashMap<String,String> change = transformAtoB(obj.getAttributes(), map.get(obj).getAttributes(), reflection);
    		
    		ret.putAll(change);
    	}
//    	System.out.println(ret.toString());
    	return ret;
    }
    
    public HashMap<String, String> convertToRavensObject(HashMap<String, RavensObject> objectsA, HashMap<String, RavensObject> objectsB, boolean flag){
    	HashSet<String> setAtoB = findMapping(objectsA, objectsB, flag);
    	//System.out.println("inside convertToRavensObject");
    	
    	//System.out.println("SetAToB: " + setAtoB.toString());
    	
    	
		//HashMap<RavensObject, RavensObject> aTobMapping = new HashMap<RavensObject, RavensObject>();
		ArrayList<Character> temp = new ArrayList<Character>();
		temp.addAll(Arrays.asList('@', '#', '$', '*', '%', '&', '^'));
		
		HashMap<String, String> ret = new HashMap<String, String>();
		
		//HashMap<String, String> transformation = new HashMap<String, String>();
		
		for (String mapping : setAtoB){
			if(temp.contains(mapping.charAt(0))){
				//System.out.println("Temp ELEMENT");
//				if(ret.containsKey("insertion")){
//					
//				}
				ret.put(getKeyToPut("insertion", ret), "object");
				continue;
			} else if (temp.contains(mapping.charAt(mapping.indexOf('-')+1))){
				ret.put(getKeyToPut("deletion", ret), "object");
				continue;
			}
			String obj1 = mapping.substring(0,mapping.indexOf('-'));
			//System.out.println(obj1);
			String obj2 =  mapping.substring(mapping.indexOf('-')+1);
			//System.out.println(obj2);
			HashMap<RavensObject, RavensObject> aTobMapping = new HashMap<RavensObject, RavensObject>();
			aTobMapping.put(objectsA.get(obj1), objectsB.get(obj2));
			
			HashMap<String, String> transformation = getTransAtoB(aTobMapping);
			
			
			for(String key: transformation.keySet()){			
					String key1 = getKeyToPut(key, ret);
					ret.put(key1, transformation.get(key));		
			}
		
			
		}
		
//		System.out.println("RET TOSTRING: " + ret.toString() + "\n");
		return ret;
    }
    
    private static String getKeyToPut(String key, HashMap<String,String> map){
    	if(!map.containsKey(key)){
    		return key;
    	}
		int count = 1;
		String keyCheck = key;
		boolean found = false;
		while (!found){
			if(map.containsKey(keyCheck)){
				count++;
				keyCheck = key+count;
			}
			else{
				found = true;
			}
		}
		return keyCheck;
	}
    
    public HashSet<String> findMapping(HashMap<String, RavensObject> objectsA, HashMap<String, RavensObject> objectsB, boolean flag){
    	ArrayList<Character> temp = new ArrayList<Character>();
    	temp.addAll(Arrays.asList('@', '#', '$', '*', '%', '&', '^'));
    	
    	//System.out.println("in mapping");
    	HashMap<String, Integer> points = new HashMap<String, Integer>();
    	points.put("insertion", 3);
    	points.put("deletion", 3);
    	points.put("shape", 2);
    	points.put("size", 1);
    	points.put("fill", 1);
    	points.put("angle", 1);
    	points.put("alignment",1);
    	points.put("reflection",1);
    	points.put("inside",0);
    	points.put("above", 1);
    	points.put("left-of", 1);
    	points.put("overlaps", 0);
    	points.put("width", 0);
    	points.put("height", 0);
    	points.put("circle", 1);
    	points.put("diamond", 0);
    	points.put("width", 1);
    	points.put("height", 1);
    	points.put("move",2);
    	
//    	points.put("star",0);
//    	points.put("square",0);
    	
    	points.put("very small", 0);
    	points.put("small",1);
    	points.put("medium",2);
    	points.put("large", 3);
    	points.put("very large",4);
    	points.put("huge",5);

    	ArrayList<String> l1 = new ArrayList<String>();
		for(String key: objectsA.keySet()){
			//System.out.println(key);
			l1.add(key);
		}
		ArrayList<String> l2 = new ArrayList<String>();
		for(String key: objectsB.keySet()){
			l2.add(key);
		}
		
		HashSet<HashSet<String>> finalList=new HashSet<HashSet<String>>();
		HashSet<String> hashset = new HashSet<String>();
		
		int min = Math.min(l1.size(), l2.size());
//		System.out.println(l1.size());
//		System.out.println(l2.size());
		
		
//		HashMap<String, Integer> newMap = new HashMap<String, Integer>();
		
		
		if((l1.size() > 5) || (l2.size() > 5)){
			//System.out.println(l1.size() + " and " + l2.size());
			//for question 3
			//System.out.println("Shape: " + objectsA.get(l1.get(0)).getAttributes().get("shape"));
			
			
			
			if(objectsA.get(l1.get(0)).getAttributes().get("shape").equals("square") || objectsB.get(l2.get(0)).getAttributes().get("shape").equals("square")){
				//System.out.println("In here!");
				HashMap<String, Integer> mapL1 = new HashMap<String, Integer>();
				HashMap<String, Integer> mapL2 = new HashMap<String, Integer>();
				
				for(int i=0;i<l1.size();i++){
					int count1=0;
					if (objectsA.get(l1.get(i)).getAttributes().get("left-of") != null){
//						mapL1.put(l1.get(i), 0);
					
		    			for(char c: objectsA.get(l1.get(i)).getAttributes().get("left-of").toCharArray()){
		    				if(c==','){
		    					count1++;
		    				}
		    			}
					}
	    			//System.out.println("In here!");
	    			if (objectsA.get(l1.get(i)).getAttributes().get("above") != null){				
		    			for(char c: objectsA.get(l1.get(i)).getAttributes().get("above").toCharArray()){
		    				if(c==','){
		    					count1++;
		    				}
		    			}
	    			}
	    			mapL1.put(l1.get(i), count1);
				}
				//System.out.println(mapL1.toString());
				for(int i=0;i<l2.size();i++){
					int count2=0;
					if (objectsB.get(l2.get(i)).getAttributes().get("left-of") != null){
		    			for(char c: objectsB.get(l2.get(i)).getAttributes().get("left-of").toCharArray()){
		    				if(c==','){
		    					count2++;
		    				}
		    			}
					}
					if (objectsB.get(l2.get(i)).getAttributes().get("above") != null){
		    			for(char c: objectsB.get(l2.get(i)).getAttributes().get("above").toCharArray()){
		    				if(c==','){
		    					count2++;
		    				}
		    			}
					}
	    			mapL2.put(l2.get(i), count2);
				}
				//System.out.println(mapL2.toString());
				for(String key: mapL1.keySet()){
					for(String key2: mapL2.keySet())
						if (mapL1.get(key).equals(mapL2.get(key2))){
							hashset.add(key + "-" + key2);
							//System.out.println("In here!");
						}
				}
				finalList.add(hashset);
			
			} else {
				
				//giving error!!
				
				/*HashMap<String, Integer> scoring = new HashMap<String, Integer>();
				int score=0;
				
				for(int i=0;i<l1.size();i++){
					for(int j=0;j<l2.size();j++){
						if(j < l1.size()){
							if(objectsA.get(l1.get(i)).getAttributes().get("shape").equals(objectsB.get(l2.get(j)).getAttributes().get("shape"))){
								score++;
							}
							if(objectsA.get(l1.get(i)).getAttributes().get("size").equals(objectsB.get(l2.get(j)).getAttributes().get("size"))){
								score++;
							}
//							if(objectsA.get(l1.get(i)).getAttributes().get("fill").equals(objectsB.get(l2.get(i)).getAttributes().get("fill"))){
//								score++;
//							}
							
						}
						scoring.put(l1.get(i) + "-" + l2.get(i), score);
					}
					Map.Entry<String, Integer> maxEntry = null;
					for (Map.Entry<String, Integer> entry : scoring.entrySet())
					{
					    if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
					    {
					        maxEntry = entry;
					    }
					}
					hashset.add(maxEntry.getKey());
					scoring.clear();
				}
				*/
				
				
				for(int i=0;i<min;i++){
					if(objectsA.get(l1.get(i)).getAttributes().get("shape").equals(objectsB.get(l2.get(i)).getAttributes().get("shape"))){						
						hashset.add(l1.get(i) + "-" + l2.get(i));
					}
				
				}
				
				int diff = l1.size()-l2.size();
				 
				 if (diff<0){
					 //hashset.add(temp.get(0) + "-" + l2.get(min-1));
					 diff = Math.abs(diff);
					 for(int i=0;i<diff;i++){
						 hashset.add(temp.get(i) + "-" + l2.get(min+i)); //changed from min+i
					 }
				 } else if(diff>0){
					 //hashset.add(l1.get(min-1)+ "-" + temp.get(0));
					 for(int i=0;i<diff;i++){
						 hashset.add(l1.get(min+i)+ "-" + temp.get(i));
					 }
				 }
	//			 System.out.println("HASHSET AFTER: " + hashset.toString());
				 finalList.add(hashset);
				 //System.out.println("FINAL LIST: " + finalList.toString());
			}
		} else {
		
			int diff = l1.size()-l2.size();
			 
			 if (diff<0){
				 diff = Math.abs(diff);
				 for(int i=0;i<diff;i++){
					 l1.add(""+temp.get(i));
				 }
			 } else if(diff>0){
				 for(int i=0;i<diff;i++){
					 l2.add(""+temp.get(i));
				 }
			 }
			 Agent.returnPermutes(l1, l2, finalList, new HashSet<String>(), l1.size());
		}
		
		
		HashMap<HashSet<String>, Integer> score = new HashMap<HashSet<String>, Integer>();
		
		for(HashSet<String> set : finalList){
			int sum = 0;
			for (String objToObj : set){
				
				if(temp.contains(objToObj.charAt(0)) || temp.contains(objToObj.charAt(objToObj.indexOf('-')+1))){
					sum +=5;
					continue;
					//System.out.println("Added sum");
				} else {
					//System.out.println(objToObj.substring(0,objToObj.indexOf('-')));
					
					RavensObject obj1 = objectsA.get(objToObj.substring(0,objToObj.indexOf('-')));
					//System.out.println(obj1.getAttributes());
					RavensObject obj2= objectsB.get(objToObj.substring(objToObj.indexOf('-')+1));
					//System.out.println(obj2.getAttributes());
					HashMap<String,String> map = new HashMap<String,String>();				
					map  = transformAtoB(obj1.getAttributes(), obj2.getAttributes(), true);
				
					for(String key : map.keySet()){					
						sum += points.get(key);
					}
				}
			}
			//System.out.println(sum);
			score.put(set, sum);
			//System.out.println("SCORE: "  + score.toString());
		}
		//System.out.println("SCORE SET: "  + score.toString());
		//System.out.println(score.toString());
		Map.Entry<HashSet<String>, Integer> minEntry = null;
		for(Map.Entry<HashSet<String>, Integer> entry : score.entrySet()){
			if (minEntry == null || entry.getValue().compareTo(minEntry.getValue()) < 0)
		    {
		        minEntry = entry;
		    }
		}
		//System.out.println("MIN ENTRY: " + minEntry + "\n");
		return minEntry.getKey();
		
    }
    
    /**
     * The primary method for solving incoming Raven's Progressive Matrices.
     * For each problem, your Agent's Solve() method will be called. At the
     * conclusion of Solve(), your Agent should return a String representing its
     * answer to the question: "1", "2", "3", "4", "5", or "6". These Strings
     * are also the Names of the individual RavensFigures, obtained through
     * RavensFigure.getName().
     * 
     * In addition to returning your answer at the end of the method, your Agent
     * may also call problem.checkAnswer(String givenAnswer). The parameter
     * passed to checkAnswer should be your Agent's current guess for the
     * problem; checkAnswer will return the correct answer to the problem. This
     * allows your Agent to check its answer. Note, however, that after your
     * agent has called checkAnswer, it will *not* be able to change its answer.
     * checkAnswer is used to allow your Agent to learn from its incorrect
     * answers; however, your Agent cannot change the answer to a question it
     * has already answered.
     * 
     * If your Agent calls checkAnswer during execution of Solve, the answer it
     * returns will be ignored; otherwise, the answer returned at the end of
     * Solve will be taken as your Agent's answer to this problem.
     * 
     * @param problem the RavensProblem your agent should solve
     * @return your Agent's answer to this problem
     */
    
    
    public static boolean similarityMetric(boolean[][] array1, boolean[][] array2){
    	int width = array1.length;
    	int height = array1[0].length;
    	double match = 0;
    	for(int i=0;i<width;i++){
    		for(int j=0;j<height;j++){
    			if(array1[i][j] == array2[i][j]){
    				match++;
    			}
    		}
    	}
    	System.out.println(match/(width*height));
    	
    	return match/(width*height) > 0.95 ;
    }
    
    /*private void drawBoolean(boolean[][] a){
    	BufferedImage imageToDraw = new BufferedImage(a.length,a[0].length, BufferedImage.TYPE_INT_RGB);
    	for (int i=0;i<a.length ; i++){
    		for (int j=0;j<a[0].length;j++){
    			if (a[i][j]){
    				imageToDraw.setRGB(i,j,0);
    			}
    			else{
    				imageToDraw.setRGB(i,j,0xFFFFFFFF);
    			}
    		}
    	}
    	
    	JFrame frame = new JFrame();
    	frame.getContentPane().setLayout(new FlowLayout());
    	frame.getContentPane().add(new JLabel(new ImageIcon(imageToDraw)));
    	frame.pack();
    	frame.setVisible(true);
    	
    }*/
    
    public static double findSimilarity(boolean[][] array1, boolean[][] array2){
    	int width = array1.length;
    	int height = array1[0].length;
    	double match = 0;
    	for(int i=0;i<width;i++){
    		for(int j=0;j<height;j++){
    			if(array1[i][j] == array2[i][j]){
    				match++;
    			}
    		}
    	}
    	System.out.println("Similarity: "  + match/(width*height));
    	return match/(width*height);
    }
    
    public static boolean[][] boolean2D(BufferedImage image){
    	int pixelColor, red, green, blue;
    	boolean[][] returnArray = new boolean[image.getWidth()][image.getHeight()];
    	for (int row = 0; row < image.getWidth(); row++) {
            for (int column = 0; column < image.getHeight(); column++) {
            	pixelColor = image.getRGB(row, column);
            	 red                 = (pixelColor & 0x00ff0000) >> 16;
				 green               = (pixelColor & 0x0000ff00) >> 8;
				 blue                =  pixelColor & 0x000000ff;
				 
				 if(red+green+blue < 50){
					 returnArray[row][column] = true;
				 } else {
					 returnArray[row][column] = false;
				 }	 
            }
    	}
    	
		return returnArray;  	
    }
    
    //define or
    
    public static boolean[][] booleanOr(boolean[][] image1, boolean[][] image2){
    	boolean[][] returnArray = new boolean[image1.length][image1[0].length];
    	for (int row = 0; row < image1.length; row++) {
            for (int column = 0; column < image1[0].length; column++) {
            	returnArray[row][column] = image1[row][column] || image2[row][column];	
            }
    	}
    	return returnArray;
    }
    
    //define and
    
    public static boolean[][] booleanAnd(boolean[][] image1, boolean[][] image2){
    	boolean[][] returnArray = new boolean[image1.length][image1[0].length];
    	for (int row = 0; row < image1.length; row++) {
            for (int column = 0; column < image1[0].length; column++) {
            	returnArray[row][column] = image1[row][column] && image2[row][column];   	
            }
    	}
    	return returnArray;
    }
    
    //define xor
    
    public static boolean[][] booleanXor(boolean[][] image1, boolean[][] image2){
    	boolean[][] returnArray = new boolean[image1.length][image1[0].length];
    	for (int row = 0; row < image1.length; row++) {
            for (int column = 0; column < image1[0].length; column++) {
            	returnArray[row][column] = image1[row][column] ^ image2[row][column];         
            }
    	}
    	return returnArray;
    }
    
    
    
    public static double numPixels(BufferedImage image1){
    	int image1_PixelColor, red, blue, green;
    	int blackPixels = 0;
    	for (int row = 0; row < image1.getWidth(); row++) {
             for (int column = 0; column < image1.getHeight(); column++) {
            	 image1_PixelColor   =  image1.getRGB(row, column);                
				 red                 = (image1_PixelColor & 0x00ff0000) >> 16;
				 green               = (image1_PixelColor & 0x0000ff00) >> 8;
				 blue                =  image1_PixelColor & 0x000000ff;
				 
				 if(red+green+blue == 0){
					 blackPixels++;
		    	 }
				 
             }
    	 }
    	return blackPixels;
    }
    
    
    public int Solve(RavensProblem problem) {
    	System.out.println( "\n" + "****NEW PROBLEM");
    	HashMap<String, RavensFigure> figures = problem.getFigures();
    	
    	RavensFigure figureA = null;
    	RavensFigure figureB = null;
    	RavensFigure figureC = null;
    	RavensFigure figureD = null;
    	RavensFigure figureE = null;
    	RavensFigure figureF = null;
    	RavensFigure figureG = null;
    	RavensFigure figureH = null;
    	
    	RavensFigure figure1 = null;
    	RavensFigure figure2 = null;
    	RavensFigure figure3 = null;
    	RavensFigure figure4 = null;
    	RavensFigure figure5 = null;
    	RavensFigure figure6 = null;
    	RavensFigure figure7 = null;
    	RavensFigure figure8 = null;
    	

    	System.out.println(problem.getProblemType());
   
//    	if(problem.getProblemType().equals("2x2")){
//    		//System.out.println("ProblemType: " +  problem.getProblemType());
//    		figureA = figures.get("A");
//        	figureB = figures.get("B");
//        	figureC = figures.get("C");
        	
        if(problem.getName().contains("B-")){
        	//System.out.println("Skip");
        	return -1;
        }
        	
        if(problem.hasVerbal()){
    		figureA = figures.get("G");
        	figureB = figures.get("H");
        	figureC = figures.get("H");
        	
        
    	} else {
    		figureA = figures.get("A");
        	figureB = figures.get("B");
        	figureC = figures.get("C");
        	figureD = figures.get("D");
        	figureE = figures.get("E");
        	figureF = figures.get("F");
        	figureG = figures.get("G");
        	figureH = figures.get("H");	
    	}

    	figure1 = figures.get("1");
    	figure2 = figures.get("2");
    	figure3 = figures.get("3");
    	figure4 = figures.get("4");
    	figure5 = figures.get("5");
    	figure6 = figures.get("6");
    	figure7 = null;
    	figure8 = null;
    	if(problem.getProblemType().equals("3x3")){
	    	figure7 = figures.get("7");
	    	figure8 = figures.get("8");
    	}
    	
    	if(problem.hasVerbal()){
    	
	    	HashMap<String, RavensObject> objectsA = figureA.getObjects();
	    	HashMap<String, RavensObject> objectsB = figureB.getObjects();
	    	HashMap<String, RavensObject> objectsC = figureC.getObjects();
	    	
	    	HashMap<String, RavensObject> objects1 = figure1.getObjects();
	    	HashMap<String, RavensObject> objects2 = figure2.getObjects();
	    	HashMap<String, RavensObject> objects3 = figure3.getObjects();
	    	HashMap<String, RavensObject> objects4 = figure4.getObjects();
	    	HashMap<String, RavensObject> objects5 = figure5.getObjects();
	    	HashMap<String, RavensObject> objects6 = figure6.getObjects();
	    	HashMap<String, RavensObject> objects7 = null;
	    	HashMap<String, RavensObject> objects8 = null;
	    	if(problem.getProblemType().equals("3x3")){
	    		objects7 = figure7.getObjects();
	        	objects8 = figure8.getObjects();
	    	}
	    	//int answerAtoB = 0;
	    	return returnAnswer(objectsA, objectsB, objectsC, objects1, objects2, objects3, objects4, objects5, objects6, objects7, objects8, false);
	    	
    	} 


    	String figA = figureA.getVisual();
    	String figB = figureB.getVisual();
    	String figC = figureC.getVisual();
    	String figD = figureD.getVisual();
    	String figE = figureE.getVisual();
    	String figF = figureF.getVisual();
    	String figG = figureG.getVisual();
    	String figH = figureH.getVisual();
    	
    	
    	
    	String fig1 = figure1.getVisual();
    	String fig2 = figure2.getVisual();
    	String fig3 = figure3.getVisual();
    	String fig4 = figure4.getVisual();
    	String fig5 = figure5.getVisual();
    	String fig6 = figure6.getVisual();
    	String fig7 = figure7.getVisual();
    	String fig8 = figure8.getVisual();
    	//System.out.println(fig1);
    	

    	BufferedImage imgA = null;
    	BufferedImage imgB = null;
    	BufferedImage imgC = null;
    	BufferedImage imgD = null;
    	BufferedImage imgE = null;
    	BufferedImage imgF = null;
    	BufferedImage imgG = null;
    	BufferedImage imgH = null;	
    	
    	BufferedImage img1 = null;
    	BufferedImage img2 = null;
    	BufferedImage img3 = null;
    	BufferedImage img4 = null;
    	BufferedImage img5 = null;
    	BufferedImage img6 = null;
    	BufferedImage img7 = null;
    	BufferedImage img8 = null;
    	
    	
    	try {
			imgA = ImageIO.read(new File(figA));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			imgB = ImageIO.read(new File(figB));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			imgC = ImageIO.read(new File(figC));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			imgD = ImageIO.read(new File(figD));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			imgE = ImageIO.read(new File(figE));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			imgF = ImageIO.read(new File(figF));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			imgG = ImageIO.read(new File(figG));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			imgH = ImageIO.read(new File(figH));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    	
    	try {
			img1 = ImageIO.read(new File(fig1));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			img2 = ImageIO.read(new File(fig2));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			img3 = ImageIO.read(new File(fig3));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			img4 = ImageIO.read(new File(fig4));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			img5 = ImageIO.read(new File(fig5));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			img6 = ImageIO.read(new File(fig6));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			img7 = ImageIO.read(new File(fig7));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			img8 = ImageIO.read(new File(fig8));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	boolean[][] boolean1 =  boolean2D(img1);
    	boolean[][] boolean2 =  boolean2D(img2);
    	boolean[][] boolean3 =  boolean2D(img3);
    	boolean[][] boolean4 =  boolean2D(img4);
    	boolean[][] boolean5 =  boolean2D(img5);
    	boolean[][] boolean6 =  boolean2D(img6);
    	boolean[][] boolean7 =  boolean2D(img7);
    	boolean[][] boolean8 =  boolean2D(img8);
    	
    	
    	
    	boolean[][] booleanA =  boolean2D(imgA);
    	boolean[][] booleanB =  boolean2D(imgB);
    	boolean[][] booleanC =  boolean2D(imgC);
    	boolean[][] booleanD =  boolean2D(imgD);
    	boolean[][] booleanE =  boolean2D(imgE);
    	boolean[][] booleanF =  boolean2D(imgF);
    	boolean[][] booleanG =  boolean2D(imgG);
    	boolean[][] booleanH =  boolean2D(imgH);
    	
    	
    	ArrayList<boolean[][]> answerList = new ArrayList<boolean[][]>();
    	answerList.add(boolean1);
    	answerList.add(boolean2);
    	answerList.add(boolean3);
    	answerList.add(boolean4);
    	answerList.add(boolean5);
    	answerList.add(boolean6);
    	answerList.add(boolean7);
    	answerList.add(boolean8);
    	
    	
    	ArrayList<Double> answerChoices = new ArrayList<Double>();
    	answerChoices.add(numPixels(img1));
    	//System.out.println(answerChoices.get(0));
    	answerChoices.add(numPixels(img2));
    	answerChoices.add(numPixels(img3));
    	answerChoices.add(numPixels(img4));
    	answerChoices.add(numPixels(img5));
    	answerChoices.add(numPixels(img6));
    	answerChoices.add(numPixels(img7));
    	answerChoices.add(numPixels(img8));

//    	System.out.println(numPixels(imgB));
//    	System.out.println(numPixels(imgH));
    	
    	
    	
    	ArrayList<boolean[][]> list1 = new ArrayList<boolean[][]>();
    	list1.add(booleanA);
    	list1.add(booleanB);
    	list1.add(booleanC);
    	
    	ArrayList<boolean[][]> list2 = new ArrayList<boolean[][]>();
    	list2.add(booleanD);
    	list2.add(booleanE);
    	list2.add(booleanF);
    	
    	double maxSim = 0;
    	double averageSim = 0;
    	int simIndex=0;
    	int shift = 0;
		for(int j=0;j<list2.size();j++){
			double sim = findSimilarity(list1.get(0), list2.get(j));
			if(sim > maxSim){
				maxSim = sim;
				simIndex=j;
			}
		}
		shift = simIndex;
		list2.remove(simIndex);
		averageSim += maxSim;
		
		maxSim = 0;
		for(int j=0;j<list2.size();j++){
			double sim = findSimilarity(list1.get(1), list2.get(j));
			if(sim > maxSim){
				maxSim = sim;
				simIndex=j;
			}
		}
		list2.remove(simIndex);
		averageSim += maxSim;
		
		maxSim = 0;
		for(int j=0;j<list2.size();j++){
			double sim = findSimilarity(list1.get(2), list2.get(j));
			if(sim > maxSim){
				maxSim = sim;
				simIndex=j;
			}
		}
		list2.remove(simIndex);
		
		averageSim += maxSim;
    	//averageSim += findSimilarity(list1.get(0), list2.get(0));
    	System.out.println("AverageSim : " + averageSim/3);
    	
    	if(averageSim/3 > 0.97){
//    		ArrayList<boolean[][]> list3 = new ArrayList<boolean[][]>();
//        	list3.add(booleanA);
//        	list3.add(booleanB);
//        	list3.add(booleanC);
//        	
//        	ArrayList<boolean[][]> list4 = new ArrayList<boolean[][]>();
//        	list4.add(booleanG);
//        	list4.add(booleanH);
    		
    		double maxSimilarity=0;
    		int indexAns = 0;
    		if(shift==1){
    			double simValue=0;	
    			for(int i=0;i<answerList.size();i++){
    				simValue = findSimilarity(booleanE, answerList.get(i));
    				if(simValue > maxSimilarity){
    					maxSimilarity = simValue;
    					indexAns = i+1;
    				}
    			}
    			
    			System.out.println("Shift Answer: " +  indexAns);
    			return indexAns;
    		}
    		if(shift==2){
    			double simValue=0;	
    			for(int i=0;i<answerList.size();i++){
    				simValue = findSimilarity(booleanD, answerList.get(i));
    				if(simValue > maxSimilarity){
    					maxSimilarity = simValue;
    					indexAns = i+1;
    				}
    			}
    			
    			System.out.println("Shift Answer: " +  indexAns);
    			return indexAns;
    		}
    		
    		
    	}		
    	
    

    	// E problem Set
    	  	
    	boolean[][] AxorB = booleanXor(booleanA, booleanB);
    	boolean[][] BxorC = booleanXor(booleanB, booleanC);
    	
    	boolean[][] AxorB_or_BxorC = booleanOr(AxorB, BxorC);
    	
    	boolean[][] DxorE = booleanXor(booleanD, booleanE);
    	boolean[][] ExorF = booleanXor(booleanE, booleanF);
    	
    	boolean[][] DxorE_or_ExorF = booleanOr(DxorE, ExorF);
    
    	
    	
    	boolean[][] AorB = booleanOr(booleanA, booleanB);
    	//System.out.println(similarityMetric(AorB, booleanC));
    	boolean[][] AandB = booleanAnd(booleanA, booleanB);
    	//boolean[][] AxorB = booleanXor(booleanA, booleanB); 
    	double orValue = findSimilarity(AorB, booleanC);
    	double andValue = findSimilarity(AandB, booleanC);
    	double xorValue = findSimilarity(AxorB, booleanC);
    	double xorOption = findSimilarity(AxorB_or_BxorC, DxorE_or_ExorF);
    	
//    	boolean[][] DorE = booleanOr(booleanD, booleanE);
    	
//    	boolean[][] AorBorC = booleanOr(AorB, booleanC);
//    	boolean[][] DorEorF = booleanOr(DorE, booleanF);
    	
    	//double rowOr = findSimilarity(AorBorC, DorEorF);
    	
    	boolean[][] GxorH = booleanXor(booleanG, booleanH);
    	boolean[][] GorH = booleanOr(booleanG, booleanH);
    	
    	ArrayList<Double> chooseOperator = new ArrayList<Double>();
    	chooseOperator.add(orValue);
    	chooseOperator.add(andValue);
    	chooseOperator.add(xorValue);
    	chooseOperator.add(xorOption);
    	//chooseOperator.add(averageSim/3);
    	//chooseOperator.add(rowOr);
    	
    	double max = 0;
    	for(int i=0; i<chooseOperator.size(); i++){
	        if(chooseOperator.get(i) > max){
	            max = chooseOperator.get(i);
	        }
	    }
    	System.out.println(chooseOperator.toString());
    	
    	if(max < 0.85){
    		if(Math.abs(numPixels(imgA) - numPixels(imgB) - numPixels(imgC)) < 100){ //changed it to absolute value
        		//System.out.println(numPixels(imgA) - numPixels(imgB) - numPixels(imgC));
        		//double min = numPixels(imgG) - numPixels(imgH) - answerChoices.get(0);
//        		if(min < 0){
//        			min = 10000;
//        		}
    			double min = Integer.MAX_VALUE;
    			//System.out.println(min);
        		int index = 0;
        		for(int i=0;i<answerChoices.size();i++){
        			//System.out.println(Math.abs(numPixels(imgG) - numPixels(imgH) - answerChoices.get(i)));
        			if(Math.abs(numPixels(imgG) - numPixels(imgH) - answerChoices.get(i)) <= min){
        				min = Math.abs(numPixels(imgG) - numPixels(imgH) - answerChoices.get(i));
        				index = i+1;
        				System.out.println("Index : " + index + " Min: " + min);
        				//findSimilarity(booleanG, answerList.get(i));  				
        				
        			}
        		}
        		
        		System.out.println("Answer Subtract: ");
    			System.out.println(index);
    			return index;
    		}
    	}
    	
    	
    	
    	
    	if(max == xorOption){
    		System.out.println("in the XOR and OR Option!!");
        	HashMap<boolean[][], Integer> map = new HashMap<boolean[][], Integer>();
        	//boolean[][] HxorI = null;
        	for(int i=0;i<answerList.size();i++){
        		map.put(booleanXor(booleanH, answerList.get(i)), i+1);       		
        	}
        	//System.out.println(map.toString());
        	HashMap<boolean[][], Integer> answer = new HashMap<boolean[][], Integer>();
        	
        	for(Map.Entry<boolean[][], Integer> entry: map.entrySet()){
        		answer.put(booleanOr(entry.getKey(), GxorH), entry.getValue());	
        	}

        	//drawBoolean(AxorB_or_BxorC);
        	//System.out.println(answer.toString());
        	double maxVal = 0;
        	int index=0;
        	for(Map.Entry<boolean[][], Integer> entry: answer.entrySet()){
        		 System.out.println("*****" + entry.getValue());
        		 if (findSimilarity(AxorB_or_BxorC, entry.getKey()) > maxVal){
        			 //System.out.println("MaxVal: " + maxVal);
        			 maxVal = findSimilarity(AxorB_or_BxorC, entry.getKey());
        			 index = entry.getValue();
        			 //return entry.getValue();
        		 }
        		 	 
        	}
        	System.out.println("MaxVal: " + maxVal + ", Index : " + index);
   		 	return index; 	
    	}

    	if(max == orValue && max == xorValue){
    		boolean[][] AorD = booleanOr(booleanA, booleanD);
        	//System.out.println(similarityMetric(AorB, booleanC));
        	boolean[][] AandD = booleanAnd(booleanA, booleanD);
        	boolean[][] AxorD = booleanXor(booleanA, booleanD); 
        	double orVertical = findSimilarity(AorD, booleanG);
        	double andVertical = findSimilarity(AandD, booleanG);
        	double xorVertical = findSimilarity(AxorD, booleanG);
        	
        	ArrayList<Double> tempList = new ArrayList<Double>();
        	tempList.add(orValue);
        	tempList.add(andValue);
        	tempList.add(xorValue);
        	max=0;
        	for(int i=0; i<tempList.size(); i++){
    	        if(tempList.get(i) > max){
    	            max = tempList.get(i);
    	        }
    	    }
        	
        	if(max == orVertical){
        		boolean[][] CtoF = booleanOr(booleanC, booleanF);
//        		System.out.println("Anything");
        		double maxSimilarity = 0;
        		int index = -1;
        		System.out.println("OR!!!");
        		
        		for(int i=0;i<answerList.size();i++){
        			double similarity = findSimilarity(CtoF, answerList.get(i));
        			if(similarity > maxSimilarity){
        				maxSimilarity = similarity;	
        				index = i+1;
        			}
        		}
        		System.out.println(index);
        		return index;
        	} else if(max == andVertical){
        		boolean[][] CtoF = booleanAnd(booleanC, booleanF);
        		System.out.println("AND!!!");
        		double maxSimilarity = 0;
        		int index = -1;
        		for(int i=0;i<answerList.size();i++){
        			double similarity = findSimilarity(CtoF, answerList.get(i));
        			//System.out.println("Anything");
        			if(similarity > maxSimilarity){
        				maxSimilarity = similarity;	
        				index = i+1;
        			}
        		}
        		System.out.println(index);
        		return index;
        	}  else if ((max == xorVertical)){
        		boolean[][] CtoF = booleanXor(booleanC, booleanF);
        		
        		System.out.println("XOR!!!");
        		double maxSimilarity = 0;
        		int index = -1;
        		for(int i=0;i<answerList.size();i++){
        			double similarity = findSimilarity(CtoF, answerList.get(i));
        			if(similarity > maxSimilarity){
        				maxSimilarity = similarity;	
        				index = i+1;
        			}
        		}
        		System.out.println(index);
        		return index;
        	}
        	
    	}
    	
    	if(max == orValue){
    		//boolean[][] GorH = booleanOr(booleanG, booleanH);
//    		System.out.println("Anything");
    		double maxSimilarity = 0;
    		int index = -1;
    		System.out.println("OR!!!");
    		for(int i=0;i<answerList.size();i++){
    			double similarity = findSimilarity(GorH, answerList.get(i));
    			if(similarity > maxSimilarity){
    				maxSimilarity = similarity;	
    				index = i+1;
    			}
    		}
    		System.out.println(maxSimilarity + " " + index);
    		return index;
    	} else if(max == andValue){
    		boolean[][] GandH = booleanAnd(booleanG, booleanH);
    		System.out.println("AND!!!");
    		double maxSimilarity = 0;
    		int index = -1;
    		for(int i=0;i<answerList.size();i++){
    			double similarity = findSimilarity(GandH, answerList.get(i));
    			//System.out.println("Anything");
    			if(similarity > maxSimilarity){
    				maxSimilarity = similarity;	
    				index = i+1;
    			}
    		}
    		System.out.println(maxSimilarity + " " + index);
    		return index;
    	}  else if ((max == xorValue)){
    		//boolean[][] GxorH = booleanXor(booleanG, booleanH);
    		//drawBoolean(GxorH);
    		System.out.println("XOR!!!");
    		double maxSimilarity = 0;
    		int index = -1;
    		for(int i=0;i<answerList.size();i++){
    			double similarity = findSimilarity(GxorH, answerList.get(i));
    			if(similarity > maxSimilarity){
    				maxSimilarity = similarity;	
    				index = i+1;
    			}
    		}
    		System.out.println(maxSimilarity + " " + index);
    		return index;
    	}
    	
    	
    	
    	//FIND THE DIFFERENCE

    	
		// E Problem Set
		    	
    	/*
    	System.out.println(numPixels(imgA) + numPixels(imgB) - numPixels(imgC));
    	if(Math.abs(numPixels(imgA) + numPixels(imgB) - numPixels(imgC)) < 150){
    		//System.out.println(numPixels(imgA) + numPixels(imgB) - numPixels(imgC));
    		double min = numPixels(imgG) + numPixels(imgH) - answerChoices.get(0);
    		int index = 0;
    		//System.out.println("Min :" + min);
    		for(int i=0;i<answerChoices.size();i++){
    			if(Math.abs(numPixels(imgG) + numPixels(imgH) - answerChoices.get(i)) <= min){	
    				min = numPixels(imgG) + numPixels(imgH) - answerChoices.get(i);
    				index = i+1;
    			}
    		}
			System.out.println("Answer Add: ");
			System.out.println(index);
			return index;

		
    	}
    	
    	System.out.println(numPixels(imgA) - numPixels(imgB) - numPixels(imgC));
    	if(Math.abs(numPixels(imgA) - numPixels(imgB) - numPixels(imgC)) < 100){ //changed it to absolute value
    		//System.out.println(numPixels(imgA) - numPixels(imgB) - numPixels(imgC));
    		double min = numPixels(imgG) - numPixels(imgH) - answerChoices.get(0);
    		if(min < 0){
    			min = 10000;
    		}
    		//System.out.println("Min :" + min);
    		int index = 0;
    		for(int i=0;i<answerChoices.size();i++){
    			
    			if(Math.abs(numPixels(imgG) - numPixels(imgH) - answerChoices.get(i)) <= min){
    				min = numPixels(imgG) - numPixels(imgH) - answerChoices.get(i);
    				index = i+1;
    				
    			}
    		}
    		System.out.println("Answer Subtract: ");
			System.out.println(index);
			return index;
    	}
    	*/
    	
    	
    	

    	System.out.println("Nothing worked!");
        return -1;
    }
        
    
}



