package com.yt.retrievalartifact.core.intercepter.impl;

import com.yt.retrievalartifact.core.intercepter.FileInterceptor;

import java.io.File;

public class FilePrintInterceptorImpl implements FileInterceptor {
    @Override
    public void apply(File file) {
        System.out.println(file.getAbsolutePath());
    }
}
