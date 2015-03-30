package edu.indiana.soic.dsc.spidal.pwa;

import com.google.common.base.Optional;
import org.apache.commons.cli.*;

public class Program {
    private static Options programOptions = new Options();
    static {
        programOptions.addOption("c", true, "Configuration file");
        programOptions.addOption("t", true, "Number of threads");
    }

    /**
     * Pairwise sequence alignment
     * @param args command line arguments to the program, which should include
     *             -c "path to config file" -t "number of threads"
     */
    public static void main(String[] args) {
        Optional<CommandLine> parserResult = Utils.parseCommandLineArguments(args, programOptions);
        if (!parserResult.isPresent()) {
            System.out.println(Utils.ERR_PROGRAM_ARGUMENTS_PARSING_FAILED);
            new HelpFormatter().printHelp(Utils.PROGRAM_NAME, programOptions);
            return;
        }

        CommandLine cmd = parserResult.get();
        if (!(cmd.hasOption("c") && cmd.hasOption("t"))) {
            System.out.println(Utils.ERR_INVALID_PROGRAM_ARGUMENTS);
            new HelpFormatter().printHelp(Utils.PROGRAM_NAME, programOptions);
            return;
        }


        readConfiguration(cmd);
    }

    public static void readConfiguration(CommandLine cmd)
    {
        config = ConfigurationMgr.LoadConfiguration(cmd.getOptionValue(Constants.CMD_OPTION_LONG_C)).pairwiseClusteringSection;
        Program.ControlFileName = cmd.getOptionValue(Constants.CMD_OPTION_LONG_C);
        PWCUtility.NodeCount = Integer.parseInt(cmd.getOptionValue(Constants.CMD_OPTION_LONG_N));
        PWCUtility.ThreadCount = Integer.parseInt(cmd.getOptionValue(Constants.CMD_OPTION_LONG_T));

        // Override section's node and thread counts with command line values if different
        if (config.getNodeCount() != PWCUtility.NodeCount) {
            config.setNodeCount(PWCUtility.NodeCount);
        }
        if (config.getThreadCount() != PWCUtility.ThreadCount) {
            config.setThreadCount(PWCUtility.ThreadCount);
        }

        PWCUtility.PointCount_Global = config.NumberDataPoints;
        Program.ProcessingOption = config.ProcessingOption;
        Program.maxNcent = config.MaxNcent;
        PWCUtility.ThreadCount = config.ThreadCount;     // Number of Threads
        PWCUtility.NodeCount = config.NodeCount;       // Number of Nodes
        PWCUtility.MPIIOStrategy = config.MPIIOStrategy;   // Controls strategy of file handling with MPI =0 is ONE FILE
        Program.ToosmalltoSplit = config.TooSmallToSplit;
        Program.Waititerations = config.WaitIterations;
        Program.MinEigtest = config.MinEigTest;
        Program.Epsi_max_change = config.EpsiMaxChange;     //converge test condition for change in epsi
        Program.InitialCoolingFactor = config.InitialCoolingFactor;  // InitialCooling Factor in Annealing
        Program.FineCoolingFactor = config.FineCoolingFactor;    // Refined Cooling Factor in Annealing
        Program.eigenvaluechange = config.EigenValueChange;    // convergence test in power method for eigenvalues
        Program.eigenvectorchange = config.EigenVectorChange;   // convergence test in power method for eigenvector
        Program.Iterationatend = config.IterationAtEnd;       // Finish up EM Loop with this number of iterations
        Program.ConvergenceLoopLimit = config.ConvergenceLoopLimit; // Limit on EM Convergence
        Program.FreezingLimit = config.FreezingLimit;       // In finish stop when all freezing measures are < FreezingLimit
        Program.PowerIterationLimit = config.PowerIterationLimit;   // Maximum iteration count in power method
        Program.ConvergeIntermediateClusters = config.ConvergeIntermediateClusters;
        PWCUtility.DebugPrintOption = config.DebugPrintOption;
        PWCUtility.ConsoleDebugOutput = config.ConsoleDebugOutput;
        Program.ContinuousClustering = config.ContinuousClustering;
        PWCUtility.Labelfile = config.LabelFile;
        PWCUtility.addMDSfile = config.AddMdsFile;
        PWCUtility.ClusterNumberfile = config.ClusterNumberFile;
        PWCUtility.addMDS = config.AddMds;
        PWCUtility.BucketFractions = config.BucketFractions;
        PWCUtility.NumberofBuckets = PWCUtility.BucketFractions != null ? PWCUtility.BucketFractions.length : 0;
        PWCUtility.NumberofCenters = config.NumberOfCenters;
        PWCUtility.CenterPointsPerCenterTypeInOutput = config.CenterPointsPerCenterTypeInOuput;
        PWCUtility.CenterPlotFile = config.CenterPlotFile;
        PWCUtility.dataTypeSize = config.getDataTypeSize();
        PWCUtility.endianness = config.isBigEndian() ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN;
        PWCUtility.isMemoryMapped = config.isMemoryMapped();
        PWCUtility.bindThreads = config.isBindThreads();
    }
}
