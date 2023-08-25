package civitas.celestis;

import org.jocl.*;

public class GPUSample {

    /*
     * Credits: ChatGPT
     */

    public static void main(String[] args) {
        // Input data
        int size = 10;
        double[] arrayA = new double[size];
        double[] arrayB = new double[size];
        double[] resultArray = new double[size];

        for (int i = 0; i < size; i++) {
            arrayA[i] = i + 1;
            arrayB[i] = (i + 1) * 2;
        }

        // Initialize JOCL
        CL.setExceptionsEnabled(true);
        cl_platform_id[] platforms = new cl_platform_id[1];
        CL.clGetPlatformIDs(1, platforms, null);
        cl_platform_id platform = platforms[0];

        cl_device_id[] devices = new cl_device_id[1];
        CL.clGetDeviceIDs(platform, CL.CL_DEVICE_TYPE_GPU, 1, devices, null);
        cl_device_id device = devices[0];

        cl_context context = CL.clCreateContext(null, 1, new cl_device_id[]{device}, null, null, null);
        cl_command_queue queue = CL.clCreateCommandQueue(context, device, 0, null);

        // Create buffers for input and output data
        cl_mem memInputA = CL.clCreateBuffer(context, CL.CL_MEM_READ_ONLY | CL.CL_MEM_COPY_HOST_PTR,
                Sizeof.cl_double * size, Pointer.to(arrayA), null);
        cl_mem memInputB = CL.clCreateBuffer(context, CL.CL_MEM_READ_ONLY | CL.CL_MEM_COPY_HOST_PTR,
                Sizeof.cl_double * size, Pointer.to(arrayB), null);
        cl_mem memOutput = CL.clCreateBuffer(context, CL.CL_MEM_WRITE_ONLY,
                Sizeof.cl_double * size, null, null);

        // Load and compile the OpenCL kernel
        String source =
                "__kernel void addDoubles(__global const double *a, __global const double *b, __global double *result) {" +
                        "    int idx = get_global_id(0);" +
                        "    result[idx] = a[idx] + b[idx];" +
                        "}";
        cl_program program = CL.clCreateProgramWithSource(context, 1, new String[]{source}, null, null);
        CL.clBuildProgram(program, 0, null, null, null, null);
        cl_kernel kernel = CL.clCreateKernel(program, "addDoubles", null);

        // Set kernel arguments
        CL.clSetKernelArg(kernel, 0, Sizeof.cl_mem, Pointer.to(memInputA));
        CL.clSetKernelArg(kernel, 1, Sizeof.cl_mem, Pointer.to(memInputB));
        CL.clSetKernelArg(kernel, 2, Sizeof.cl_mem, Pointer.to(memOutput));

        // Execute the kernel
        long[] globalWorkSize = {size};
        CL.clEnqueueNDRangeKernel(queue, kernel, 1, null, globalWorkSize, null, 0, null, null);

        // Read the result back to Java
        CL.clEnqueueReadBuffer(queue, memOutput, CL.CL_TRUE, 0, Sizeof.cl_double * size,
                Pointer.to(resultArray), 0, null, null);

        // Clean up
        CL.clReleaseMemObject(memInputA);
        CL.clReleaseMemObject(memInputB);
        CL.clReleaseMemObject(memOutput);
        CL.clReleaseKernel(kernel);
        CL.clReleaseProgram(program);
        CL.clReleaseCommandQueue(queue);
        CL.clReleaseContext(context);

        // Print the result
        for (int i = 0; i < size; i++) {
            System.out.println("Result[" + i + "] = " + resultArray[i]);
        }
    }
}
