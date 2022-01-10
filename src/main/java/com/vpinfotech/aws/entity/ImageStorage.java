package com.vpinfotech.aws.entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;

@Entity
@Table(name="aws_file_storage")
public class ImageStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int aws_id;
    private String aws_fileName;

    public ImageStorage() {
    }

    public ImageStorage(String aws_fileName) {
        this.aws_fileName = aws_fileName;
    }

    public int getAws_id() {
        return aws_id;
    }

    public void setAws_id(int aws_id) {
        this.aws_id = aws_id;
    }

    public String getAws_fileName() {
        return aws_fileName;
    }

    public void setAws_fileName(String awsFileName) {
        this.aws_fileName = aws_fileName;
    }

    @Override
    public String toString() {
        return "ImageStorage{" +
                "aws_id=" + aws_id +
                ", aws_fileName='" + aws_fileName + '\'' +
                '}';
    }
}
