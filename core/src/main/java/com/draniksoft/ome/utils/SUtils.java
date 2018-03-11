package com.draniksoft.ome.utils;


import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.utils.struct.MtPair;

/**
 *
 * Utils for code shortening
 *
 */
public class SUtils {

    public static String getS(String k) {
        return AppDO.I.L().get(k);
    }

    public static boolean parseAssetURI(String uri, MtPair<String, String> pair) {
	  int fa = -1;
	  for (int i = 0; i < uri.length(); i++) {
		char c = uri.charAt(i);
		if (c == '@') {
		    if (fa < 0) fa = i;
		}
	  }
	  if (fa < 0) return false;
	  String ext = uri.substring(0, fa);
	  String other = uri.substring(fa + 1);
	  pair.K(ext);
	  pair.V(other);
	  return true;
    }

    public static MtPair<String, String> parseAssetURI(String uri) {
	  MtPair<String, String> p = new MtPair<String, String>();
	  if (parseAssetURI(uri, p)) {
		return p;
	  } else {
		return null;
	  }
    }



}
