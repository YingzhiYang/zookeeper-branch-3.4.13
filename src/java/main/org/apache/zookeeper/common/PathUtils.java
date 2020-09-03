 /**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.zookeeper.common;


/**
 * Path related utilities
 */    
public class PathUtils {
	
	/** validate the provided znode path string
	 * @param path znode path string
	 * @param isSequential if the path is being created
	 * with a sequential flag
	 * @throws IllegalArgumentException if the path is invalid
	 */
	public static void validatePath(String path, boolean isSequential) 
		throws IllegalArgumentException {
	    //如果是顺序节点路径加1进行验证，如果是顺序节点就会在后面加1，就变成不是以“/”结尾的节点
        //比如，创建节点create /r/ 这样会报错，但是创建顺序节点create -s /r/ 因为最后加了1所以就不会报错，可以进行正常验证
        //此处只是在验证path是不是顺序节点，并没有修改任何输入的字符
		validatePath(isSequential? path + "1": path);
	}
	
    /**
     * Validate the provided znode path string
     * @param path znode path string
     * @throws IllegalArgumentException if the path is invalid
     */
    public static void validatePath(String path) throws IllegalArgumentException {
        if (path == null) {//空报错
            throw new IllegalArgumentException("Path cannot be null");
        }
        if (path.length() == 0) {//长度0报错
            throw new IllegalArgumentException("Path length must be > 0");
        }
        if (path.charAt(0) != '/') {//不是“/”开头的也报错
            throw new IllegalArgumentException(
                         "Path must start with / character");
        }
        if (path.length() == 1) { // done checking - it's the root 长度是1说明就只有一个/，直接返回
            return;
        }
        if (path.charAt(path.length() - 1) == '/') {//如果最后一个节点是斜线，也报错
            throw new IllegalArgumentException(
                         "Path must not end with / character");
        }

        //错误提示reason
        String reason = null;
        char lastc = '/';
        char chars[] = path.toCharArray();
        char c;
        //循环检查每一个字符是否合法
        for (int i = 1; i < chars.length; lastc = chars[i], i++) {
            c = chars[i];

            if (c == 0) {//空是不允许的比如 create /rr/ /r
                reason = "null character not allowed @" + i;
                break;
            } else if (c == '/' && lastc == '/') {//两个//也不允许，比如create /rr//r
                reason = "empty node name specified @" + i;
                break;
            } else if (c == '.' && lastc == '.') {//相对路径也不允许，比如create /rr/../r
                if (chars[i-2] == '/' &&
                        ((i + 1 == chars.length)
                                || chars[i+1] == '/')) {
                    reason = "relative paths not allowed @" + i;
                    break;
                }
            } else if (c == '.') {//.也不允许，比如create /rr./r
                if (chars[i-1] == '/' &&
                        ((i + 1 == chars.length)
                                || chars[i+1] == '/')) {
                    reason = "relative paths not allowed @" + i;
                    break;
                }
            } else if (c > '\u0000' && c < '\u001f'
                    || c > '\u007f' && c < '\u009F'
                    || c > '\ud800' && c < '\uf8ff'
                    || c > '\ufff0' && c < '\uffff') {//特殊字符不允许
                reason = "invalid character @" + i;
                break;
            }
        }
        //如果错误提示不是null报异常
        if (reason != null) {
            throw new IllegalArgumentException(
                    "Invalid path string \"" + path + "\" caused by " + reason);
        }
    }
}
