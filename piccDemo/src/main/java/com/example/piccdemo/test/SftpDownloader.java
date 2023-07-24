package com.example.piccdemo.test;

import com.jcraft.jsch.*;

import java.io.FileOutputStream;
import java.util.Properties;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/7/19 8:56
 * @注释
 */



    public class SftpDownloader {
        public static void main(String[] args) {
            String hostname = "";  // SFTP服务器主机名
            int port = 50002;  // SFTP服务器端口号
            String username = "";  // SFTP服务器用户名
            String password = "";  // SFTP服务器密码
            String remoteFilePath = "t";  // 远程文件路径
            String localFilePath = "";  // 本地文件路径

            JSch jSch = new JSch();

            try {
                // 创建会话
                Session session = jSch.getSession(username, hostname, port);
                session.setPassword(password);

                // 配置会话选项
                Properties config = new Properties();
                config.put("StrictHostKeyChecking", "no");
                session.setConfig(config);

                // 连接会话
                session.connect();

                // 打开SFTP通道
                ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
                channelSftp.connect();

                // 下载文件
                channelSftp.get(remoteFilePath, new FileOutputStream(localFilePath));

                // 关闭通道和会话
                channelSftp.disconnect();
                session.disconnect();

                System.out.println("文件下载完成。");
            } catch (JSchException | SftpException | java.io.FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


